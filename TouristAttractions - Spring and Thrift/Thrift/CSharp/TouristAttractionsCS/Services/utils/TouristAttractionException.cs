using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Services
{
    public class TouristAttractiontException : Exception
    {
        public TouristAttractiontException() : base() { }

        public TouristAttractiontException(String msg) : base(msg) { }

        public TouristAttractiontException(String msg, Exception e) : base(msg, e) { }
    }
}
