using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Networking
{
    [Serializable]
    public class AgencyUserDTO
    {
        public String userName { get; set; }
        public String password { get; set; }

        public AgencyUserDTO(String userName, String password)
        {
            this.userName = userName;
            this.password = password;
        }
    }
}
