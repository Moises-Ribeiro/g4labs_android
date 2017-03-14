using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Web;

namespace G4Labs.model
{
    [DataContract]
    public class Task
    {
        [DataMember]
        public int Id { get; set; }

        [DataMember]
        public string Title { get; set; }

        [DataMember]
        public int Completed { get; set; }

        [DataMember]
        public string Description { get; set; }
    }
}