package com.sm.jax;

import static java.util.Arrays.asList;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.sm.jax.model.Resto;

@Path("/res")
public class HelloWorldService {

	private String s;

	@GET
	@Path("/hello")
	 @Produces(MediaType.APPLICATION_JSON)
	public Resto sayHello() {
		Resto r = new Resto();
		r.setName("La tour d'argent");
//		return new Document()
//		.append("testis", "ok").toJson();
		return r;
	}

	@GET
	@Path("/init")
	// @Produces(MediaType.APPLICATION_JSON)
	public String findOne() {
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase("newyork");
		// db.getCollection("restaurants").find({ "grades.grade": "B" });
		FindIterable<Document> iterable = db.getCollection("restaurants").find(
				new Document("restaurant_id", "41704620"));
		if (iterable==null) {
			return "<h4>Init already done";
		}
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",
				Locale.ENGLISH);
		try {
			db.getCollection("restaurants")
					.insertOne(
							new Document("address", new Document()
									.append("street", "2 Avenue")
									.append("zipcode", "10075")
									.append("building", "1480")
									.append("coord",
											asList(-73.9557413, 40.7720266)))
									.append("borough", "Manhattan")
									.append("cuisine", "Italian")
									.append("grades",
											asList(new Document()
													.append("date",
															format.parse("2014-10-01T00:00:00Z"))
													.append("grade", "A")
													.append("score", 11),
													new Document()
															.append("date",
																	format.parse("2014-01-16T00:00:00Z"))
															.append("grade",
																	"B")
															.append("score", 17)))
									.append("name", "Vella")
									.append("restaurant_id", "41704620"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "<h3>Init done OK</h3>";
	}

	@GET
	@Path("/check")
	// @Produces(MediaType.APPLICATION_JSON)
	public String check() {
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase("newyork");
		s = "nores";
		FindIterable<Document> iterable = db.getCollection("restaurants").find(
				new Document("restaurant_id", "41704620"));
		// .find({ "restaurant_id": "41704620" }).
		if (iterable==null) {
			return "aucun resto trouvé";
		}
		iterable.forEach(new Block<Document>() {
			@Override
			public void apply(final Document document) {
				s = document.toJson();
			}
		});

		return "<h3>" + s + "</h3>";
	}
}