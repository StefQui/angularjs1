package com.sm.jax.auth;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.sm.jax.model.User;
import com.sm.jax.utils.UserConverter;

/**
 * REST Service to expose the data to display in the UI grid.
 *
 * @author Roberto Cortez
 */
@Path("/auth")
public class AuthService {

	private String s;
	@javax.ws.rs.core.Context
	ServletContext context;

	@GET
	@Path("/sayHello")
	public String sayHello() {
		return "<h1>Hello World from json</h1>";
	}

	@POST
	@Path("/sendRegistration")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User oldzzzzzzzzzzzzztryRegister(User user) {
		MongoClient mongoClient = (MongoClient) context
				.getAttribute("MONGO_CLIENT");
		MongoDatabase db = mongoClient.getDatabase("newyork");

		// FindIterable<Document> iterable = db.getCollection("users").find(
		// new Document("_id", "41704620"));
		// if (iterable==null) {
		// return "<h4>Init already done";
		// }
		// DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",
		// Locale.ENGLISH);
		Document a = UserConverter.toDocument(user);
		db.getCollection("users").insertOne(a);

		return UserConverter.toUser(a);
	}
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User tryRegister(User user) {
		MongoClient mongoClient = (MongoClient) context
				.getAttribute("MONGO_CLIENT");
		MongoDatabase db = mongoClient.getDatabase("newyork");

		// FindIterable<Document> iterable = db.getCollection("users").find(
		// new Document("_id", "41704620"));
		// if (iterable==null) {
		// return "<h4>Init already done";
		// }
		// DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",
		// Locale.ENGLISH);
		Document a = UserConverter.toDocument(user);
		db.getCollection("users").insertOne(a);

		return UserConverter.toUser(a);
	}

//	@GET
//	@Path("/check")
//	// @Produces(MediaType.APPLICATION_JSON)
//	public String check() {
//		MongoClient mongoClient = new MongoClient();
//		MongoDatabase db = mongoClient.getDatabase("newyork");
//		s = "nores";
//		FindIterable<Document> iterable = db.getCollection("restaurants").find(
//				new Document("restaurant_id", "41704620"));
//		// .find({ "restaurant_id": "41704620" }).
//		if (iterable == null) {
//			return "aucun resto trouvé";
//		}
//		iterable.forEach(new Block<Document>() {
//			@Override
//			public void apply(final Document document) {
//				s = document.toJson();
//			}
//		});
//
//		return "<h3>" + s + "</h3>";
//	}
}