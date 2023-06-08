package DAO;

import org.example.DAO.DaoInvitationImpl;
import org.example.models.Invitation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DaoInvitationImplTest {


    @Test
    void createInvite() {
        DaoInvitationImpl daocheck = new DaoInvitationImpl();
//        Invitation i1 = new Invitation( 1, Invitation.generatHashcode(), 37, 2, true, 2);
//        daocheck.createInvite(i1);
        Invitation inviteById = daocheck.getInviteById(1);
        System.out.println(inviteById.toString());
    }

    @Test
    void CheckChangeStatusInvite() {
        DaoInvitationImpl daocheck= new DaoInvitationImpl();
        Invitation invite_1 = new Invitation(1, "978z65icci", 37, 2, false,  1);
        daocheck.changeStatusInvite(1,false);
        Invitation invite_new = daocheck.getInviteById(1);
        assertEquals(invite_1.isActive(), invite_new.isActive());

    }

    @Test
    void CheckChangeInvitedInInvite() {
        DaoInvitationImpl daocheck= new DaoInvitationImpl();
        Invitation invite_1 = new Invitation(1, "978z65icci", 37, 2, false,  1);
        daocheck.changeInvitedInInvite(1,1);
        Invitation invite_new = daocheck.getInviteById(1);
        assertEquals(invite_1.getIdInv(), invite_new.getIdInv());
    }

    @Test
    void EqualsGetInviteByHashCode() {
        Invitation invite1 = new Invitation(1,"978z65icci",37,2, false, 1);
        DaoInvitationImpl daocheck= new DaoInvitationImpl();
        Invitation invite_2 = daocheck.getInviteByHashCode("978z65icci");
        assertEquals(invite1.getHashcode(),invite_2.getHashcode());

    }

    @Test
    void EqualsGetInviteById() {
        Invitation invite1 = new Invitation(1,"978z65icci",37,2, false, 1);
        DaoInvitationImpl daocheck= new DaoInvitationImpl();
        Invitation invite_2 = daocheck.getInviteById(1);
        assertEquals(invite1.getId(),invite_2.getId());
        assertEquals(invite1.getTeam(),invite_2.getTeam());
        assertEquals(invite1.getRole(),invite_2.getRole());


    }

}