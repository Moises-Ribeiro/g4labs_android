using System.Collections.Generic;
using System.ServiceModel;
using System.ServiceModel.Web;

namespace G4Labs
{
    [ServiceContract]
    public interface ITask
    {

        [OperationContract]
        [WebGet(UriTemplate = "/api/tasks",
                ResponseFormat = WebMessageFormat.Json,
                BodyStyle = WebMessageBodyStyle.Wrapped)]
        IList<model.Task> GetTasks();

        [OperationContract]
        [WebGet(UriTemplate = "/api/tasks/{id}",
                ResponseFormat = WebMessageFormat.Json,
                BodyStyle = WebMessageBodyStyle.Wrapped)]
        model.Task GetTask(string id);

    }
}
