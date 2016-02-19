package com.sm.jax;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.servlet.ServletContext;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import com.sm.jax.model.Resto;
import com.sm.jax.utils.RestoConverter;

/**
 * REST Service to expose the data to display in the UI grid.
 *
 * @author Roberto Cortez
 */
@Stateless
@ApplicationPath("/resources")
@Path("restos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RestoResource extends Application {
	private int nb;
	
	
	@javax.ws.rs.core.Context 
	ServletContext context;
	
	private Integer countRestos() {
//		MongoClient mongoClient = new MongoClient();
		MongoClient mongoClient = (MongoClient) context
                .getAttribute("MONGO_CLIENT");	
		MongoDatabase db = mongoClient.getDatabase("newyork");
		nb = 0;
		FindIterable<Document> iterable = db.getCollection("restaurants")
				.find();
		// .find({ "restaurant_id": "41704620" }).
		if (iterable == null) {
			return 0;
		}
		iterable.forEach(new Block<Document>() {
			@Override
			public void apply(final Document document) {
				nb++;
			}
		});

		// Query query =
		// entityManager.createQuery("SELECT COUNT(p.id) FROM Person p");
		return nb;
	}

	@SuppressWarnings("unchecked")
	private List<Resto> findRestos(int startPosition, int maxResults,
			String sortFields, String sortDirections) {

		MongoClient mongoClient = (MongoClient) context
                .getAttribute("MONGO_CLIENT");	
//		MongoClient mongoClient = new MongoClient();
//		MongoClient mongoClient = new MongoClient();
		// DB db = mongoClient.getDB("mydb");
		// DBCollection collection = db.getCollection("test");

		MongoDatabase db = mongoClient.getDatabase("newyork");
		nb = 0;
		MongoCollection<Document> myCollection = db
				.getCollection("restaurants");
		FindIterable<Document> rs = myCollection.find().skip(startPosition).limit(10);
		// FindIterable<Document> myCursor=myCollection.find().sort(new
		// BasicDBObject("date",-1)).limit(10);
		System.out.println("Find restos");
		final List<Resto> restos = new ArrayList<>();
		rs.forEach(new Block<Document>() {
			@Override
			public void apply(final Document document) {
				Resto r = RestoConverter.toResto(document);
//				ObjectId ii = ((ObjectId)document.get( "_id" ));
//				System.out.println("Find restos : loop : "+ii+" "+ii.toString());
//				r.setRestoId(ii.toString());
//				r.setName(document.getString("name"));
//				r.setDescription(document.getString("description"));
				restos.add(r);
			}
		});
		return restos;
		//
		// Query query =
		// entityManager.createQuery("SELECT p FROM Person p ORDER BY " +
		// sortFields + " " + sortDirections);
		// query.setFirstResult(startPosition);
		// query.setMaxResults(maxResults);
		// return query.getResultList();
	}

	private PaginatedListWrapper<Resto> findRestos(
			PaginatedListWrapper<Resto> wrapper) {
		wrapper.setTotalResults(countRestos());
		int start = (wrapper.getCurrentPage() - 1) * wrapper.getPageSize();
		wrapper.setList(findRestos(start, wrapper.getPageSize(),
				wrapper.getSortFields(), wrapper.getSortDirections()));
		return wrapper;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public PaginatedListWrapper<Resto> listRestos(
			@DefaultValue("1") @QueryParam("page") Integer page,
			@DefaultValue("id") @QueryParam("sortFields") String sortFields,
			@DefaultValue("asc") @QueryParam("sortDirections") String sortDirections) {
		PaginatedListWrapper<Resto> paginatedListWrapper = new PaginatedListWrapper<>();
		paginatedListWrapper.setCurrentPage(page);
		paginatedListWrapper.setSortFields(sortFields);
		paginatedListWrapper.setSortDirections(sortDirections);
		paginatedListWrapper.setPageSize(10);
		return findRestos(paginatedListWrapper);
	}

	@GET
	@Path("{id}")
	public Resto getResto(@PathParam("id") String id) {
		MongoClient mongoClient = (MongoClient) context
                .getAttribute("MONGO_CLIENT");	
//		MongoClient mongoClient = new MongoClient();
		MongoCollection<Document> myCollection = mongoClient.getDatabase(
				"newyork").getCollection("restaurants");
		System.out.println("getResto id="+id);
		ObjectId oid = new ObjectId(id);
		FindIterable<Document> rs = myCollection
				.find(eq("_id", oid));
		System.out.println("getResto idafter="+rs);
		Document res = rs.iterator().next();
		Resto resto = RestoConverter.toResto(res);
//		resto.setRestoId(res.get("_id").toString());
//		resto.setName(res.getString("name"));
//		resto.setDescription(res.getString("description"));
		return resto;
	}

	@POST
	public Resto saveResto(Resto resto) {
		MongoClient mongoClient = (MongoClient) context
                .getAttribute("MONGO_CLIENT");	
//		MongoClient mongoClient = new MongoClient();
		MongoCollection<Document> myCollection = mongoClient.getDatabase(
				"newyork").getCollection("restaurants");
		if (resto.getRestoId() == null) {
			Document toSave = RestoConverter.toDocument(resto);
//			toSave.append("name", resto.getName());
//			toSave.append("description", resto.getDescription());
			myCollection.insertOne(toSave);
		} else {
//			Resto toUpdate = getResto(resto.getRestoId());
//			toUpdate.setName(resto.getName());
//			toUpdate.setDescription(resto.getDescription());
//			Document toSave = RestoConverter.toDocument(resto);
			ObjectId oid = new ObjectId(resto.getRestoId());
//			UpdateResult n = myCollection.updateOne(
//					new Document("_id", oid),
//					new Document("$set", new Document("name", resto.getName())
//							.append("description", resto.getDescription())));
			UpdateResult n = myCollection.updateOne(
					new Document("_id", oid),
					new Document("$set", RestoConverter.toDocument(resto)));
		}

		return resto;
	}

	@DELETE
	@Path("{id}")
	public void delete(@PathParam("id") String id) {
		MongoClient mongoClient = (MongoClient) context
                .getAttribute("MONGO_CLIENT");	
//		MongoClient mongoClient = new MongoClient();
		MongoCollection<Document> myCollection = mongoClient.getDatabase(
				"newyork").getCollection("restaurants");
		ObjectId oid = new ObjectId(id);
		myCollection.deleteMany(new Document("_id", oid));
	}
}
