from django.urls import path
from user.views import UserLoginView, UserRegistrationView, UserView, UsersView

urlpatterns = [
    path('login/', UserLoginView.as_view(), name='login-user'),
    path('register/', UserRegistrationView.as_view(), name='register-user'),
    path('getUser/<int:user_id>', UserView.as_view(), name='get-user'),
    path('getUsers/<int:team_id>', UsersView.as_view(), name='get-users'),
]

