using System;

namespace TouristAttractions.model
{
    [Serializable]
    public class AgencyUser : Entity<int>
    {
        public AgencyUser()
        {
        }

        public AgencyUser(int Id, String UserName, String Password)
        {
            this.UserName = UserName;
            this.Password = Password;
            this.Id = Id;
        }

        public string UserName { get; set;}
        public string Password{ get; set;}

        public override string ToString()
        {
            return "UserName: " + UserName + " Password: " + Password;
        }
    }
}
