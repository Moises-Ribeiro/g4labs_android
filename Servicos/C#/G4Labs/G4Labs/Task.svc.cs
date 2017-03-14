using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;

namespace G4Labs
{
    public class Task : ITask
    {
        public model.Task GetTask(string id)
        {
            controller.Task controller = new controller.Task();
            return controller.GetTask(Convert.ToInt32(id));
        }

        public IList<model.Task> GetTasks()
        {
            controller.Task controller = new controller.Task();
            return controller.GetTasks();
        }
    }
}
