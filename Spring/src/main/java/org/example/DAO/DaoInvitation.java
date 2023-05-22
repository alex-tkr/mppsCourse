package org.example.DAO;

import org.example.models.Invitation;

public interface DaoInvitation {
    String createInvite(Invitation invitation);
    void changeStatusInvite(int id_invite,boolean status);
    Invitation getInviteByHashCode(String hashcode);
    Invitation getInviteById(int id);
}
