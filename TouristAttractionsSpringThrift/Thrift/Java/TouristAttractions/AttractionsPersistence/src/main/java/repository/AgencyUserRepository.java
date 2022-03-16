package repository;

import model.AgencyUser;

public interface AgencyUserRepository extends Repository<AgencyUser, Integer> {
    public AgencyUser filterAgencyUserByUserNameAndPassword(String userName, String password);
}