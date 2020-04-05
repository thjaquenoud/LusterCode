package edu.cooper.ee.ece366.LusterCode;

import edu.cooper.ee.ece366.LusterCode.service.AnswerService;
import edu.cooper.ee.ece366.LusterCode.store.AnswerStoreJdbi;
import edu.cooper.ee.ece366.LusterCode.handler.AnswerHandler;
import edu.cooper.ee.ece366.LusterCode.util.JsonTransformer;
import com.google.gson.Gson;
import org.jdbi.v3.core.Jdbi;
import spark.Spark;

public class Main {
    public static void main(String[] args) {
        Gson gson = new Gson();
        String url = "jdbc:mysql://localhost:3306/LusterCode?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=EST";
        String username = "main";
        String password = "Lusterman";
        Jdbi jdbi = Jdbi.create(url, username, password);
        AnswerStoreJdbi answerStore = new AnswerStoreJdbi(jdbi);
        answerStore.populateDb();
        AnswerService answerService = new AnswerService(answerStore);
        AnswerHandler answerHandler = new AnswerHandler(answerService, gson);
        JsonTransformer jsonTransformer = new JsonTransformer();

        Spark.get("/ping", (req, res) -> "OK");
        // user routing
        // Spark.post("/user", (req, res) -> userHandler.createUser(req), jsonTransformer);
        // Spark.get("/user/:userID", (req, res) -> userHandler.getUser(req), jsonTransformer);
        // post routing
        // Spark.post("/post", (req, res) -> postHandler.createPost(req), jsonTransformer);
        // Spark.get("/post/:postID", (req, res) -> postHandler.getPost(req), jsonTransformer);
        // answer routing
        Spark.post("/answer", answerHandler::createAnswer, gson::toJson);
        Spark.get("/answer/:answerID", answerHandler::getAnswer, gson::toJson);
        Spark.delete("/answer/:answerID", answerHandler::deleteAnswer, gson::toJson);



       /* PostStore postStore = new PostStoreImpl();
        AnswerStore answerStore = new AnswerStoreImpl();
        final Handler myHandler = new Handler(postStore, answerStore);
        //Basic Hello World response
        get("/ping", (req, res) -> "OK");

        //Determine what to do with 2-field string
        get("/:action/:field1", (req,res)-> {
            String action = req.params(":action");
            //Call some handler method depending on the specified action
            if (action.contains("returnPost")){
                String handlerReply = myHandler.returnPost(req);
                return "Hello: Post Requested\n" + handlerReply + "\n";
            }
            if (action.contains("getAnswers")){
                return myHandler.getAnswersHandler(req);
            }
            else { return "Hello: Nothing Happened" + "\n";}
        });

        //Determine what to do with 3-field string
        get("/:action/:field1/:field2", (req,res)-> {
            String action = req.params(":action");
            //Call some handler method depending on the specified action
            if (action.contains("removeUser")){
                System.out.print("removeUser selected\n");
                String handlerReply = myHandler.userRemover(req);
                return "Hello: User Deletion Requested\n" + handlerReply + "\n";
            }
            else if (action.contains("addTags")){
                String handlerReply = myHandler.postAddTags(req);
                return handlerReply;
            }
            return "Hello: Nothing Happened" + "\n"; //None of the conditions were met, and so nothing was done
        });


        //Determine what to do with 4-field string
        get("/:action/:field1/:field2/:field3", (req,res)-> {
            String action = req.params(":action");
            //If 'action' has called for creation of a new user, do so and report
            if (action.contains("passChange")){
                String handlerReply = myHandler.passChange(req);
                return "Hello: Password Change Requested\n" + handlerReply + "\n";
            }

            return "Hello: Nothing Happened" + "\n"; //None of the conditions were met, and so nothing was done
        });

        //Determine what to do with 5-field string
        get("/:action/:field1/:field2/:field3/:field4", (req,res)-> {
            String action = req.params(":action");
            //for answering, format: /answer/username/askpostid/type/content
            if (action.contains("answer")){
                String handlerReply = myHandler.answerHandler(req);
                return "Hello: Answer Requested\n" + handlerReply + "\n";
            }
            //for answering, format: /newPost/username/type/tags/postcontent
            if (action.contains("newPost")){
                String handlerReply = myHandler.postCreate(req);
                return "Hello: New Post Requested\n" + handlerReply + "\n";
            }
            return "Hello: Nothing Happened" + "\n"; //None of the conditions were met, and so nothing was done
        });


        //Determine what to do with 6-field string
        get("/:action/:field1/:field2/:field3/:field4/:field5", (req,res)-> {
            String action = req.params(":action");
            //Call some handler method depending on the specified action
            if (action.contains("newUser")){
                String handlerReply = myHandler.userHandler(req);
                return "Hello: New User Requested\n" + handlerReply + "\n";
            }
            return "Hello: Nothing Happened" + "\n"; //None of the conditions were met, and so nothing was done
        });*/

    }
}
