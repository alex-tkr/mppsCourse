from django.urls import reverse
from rest_framework import status
from rest_framework.test import APIClient, APITestCase

from API.models import Team
from project.models import Project, Task
from user.views import User
from django.test import TestCase
from project.models import Comment


class TaskListViewTestCase(TestCase):
    def setUp(self):
        self.client = APIClient()
        self.user = User.objects.create_user(username='testuser', password='12345')
        self.team = Team.objects.create(name='Test', user=self.user)
        self.project = Project.objects.create(name='Test Project', owner=self.user, team=self.team)
        self.task = Task.func.create(title='Test Task', project=self.project, created_user=self.user)
        self.user.team = self.team
        self.user.role = '1'

    def test_create(self):
        url = reverse('task', kwargs={'project_id': self.project.id})
        data = {'title': 'New Task', 'project': self.project.id, 'team': self.team.id}
        self.client.force_authenticate(user=self.user)
        response = self.client.post(url, data)
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        task = Task.func.get(pk=response.data['id'])
        self.assertEqual(task.title, data['title'])
        self.assertEqual(task.project, self.project)
        self.assertEqual(task.created_user, self.user)

    def test_update(self):
        url = reverse('task', kwargs={'project_id': self.project.id})
        data = {'title': 'Updated Task', 'task': self.task.id, "project": self.project.id}
        self.client.force_authenticate(user=self.user)
        response = self.client.patch(url, data)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        task = Task.func.get(pk=self.task.id)
        self.assertEqual(task.title, data['title'])

    def test_destroy(self):
        url = reverse('task', kwargs={'project_id': self.project.id})
        data = {'task_id': self.task.id, 'task': self.task.id, "project": self.project.id}
        self.client.force_authenticate(user=self.user)
        response = self.client.delete(url, data)
        self.assertEqual(response.status_code, status.HTTP_202_ACCEPTED)
        with self.assertRaises(Task.DoesNotExist):
            Task.func.get(pk=self.task.id)


class CommentAPIViewTestCase(APITestCase):
    def setUp(self):
        self.user = User.objects.create_user(username='testuser', password='12345')
        self.team = Team.objects.create(name='Test', user=self.user)
        self.project = Project.objects.create(name='Test Project', owner=self.user, team=self.team)
        self.user.team = self.team
        self.user.role = '1'
        self.task = Task.func.create(
            title='Test Task',
            description='Task description',
            priority='1',
            created_user=self.user,
            project=self.project
        )
        self.comment = Comment.objects.create(
            content='Test comment',
            task=self.task,
            commenter=self.user
        )

    def test_create_comment(self):
        url = reverse('comment_api', kwargs={'task_id': self.task.pk, 'team_id': self.team.pk})
        data = {'content': 'New Comment'}
        self.client.force_authenticate(user=self.user)
        response = self.client.post(url, data)
        self.assertEqual(response.status_code, 201)
        self.assertEqual(Comment.objects.count(), 2)

    def test_update_comment(self):
        url = reverse('comment_api', kwargs={'task_id': self.task.id, 'team_id': self.team.id})
        data = {'content': 'Updated Comment', 'comment_id': self.comment.id}
        self.client.force_authenticate(user=self.user)
        response = self.client.put(url, data=data)
        self.assertEqual(response.status_code, 200)
        self.comment.refresh_from_db()
        self.assertEqual(self.comment.content, 'Updated Comment')



    def test_delete_comment(self):
        url = reverse('comment_api', kwargs={'task_id': self.task.id, 'team_id': self.team.id})
        data = {'comment_id': self.comment.id}
        self.client.force_authenticate(user=self.user)
        response = self.client.delete(url, data=data)
        self.assertEqual(response.status_code, 202)
        self.assertFalse(Comment.objects.filter(id=self.comment.id).exists())


class ProjectAPIViewTestCase(APITestCase):
    def setUp(self):
        self.user = User.objects.create_user(username='testuser', password='12345')
        self.team = Team.objects.create(name='Test', user=self.user)
        self.project = Project.objects.create(name='Test Project', owner=self.user, team=self.team)
        self.user.team = self.team
        self.user.role = '1'

    def test_create_project(self):
        url = reverse('project_list')
        data = {'name': 'Test_2', 'description': 'Test_2'}
        self.client.force_authenticate(user=self.user)
        response = self.client.post(url, data=data)
        self.assertEqual(response.status_code, 201)
        self.assertEqual(Project.objects.count(), 2)

    def test_update_project(self):
        url = reverse('project_list')
        data = {'project_id': self.project.id, 'name': 'New'}
        self.client.force_authenticate(user=self.user)
        response = self.client.put(url, data=data)
        self.assertEqual(response.status_code, 200)
        self.project.refresh_from_db()
        self.assertEqual(self.project.name, 'New')

    def test_delete_project(self):
        url = reverse('project_list')
        data = {'project_id': self.project.id}
        self.client.force_authenticate(user=self.user)
        response = self.client.delete(url, data=data)
        self.assertEqual(response.status_code, 202)
        self.assertFalse(Project.objects.filter(id=self.project.id).exists())
