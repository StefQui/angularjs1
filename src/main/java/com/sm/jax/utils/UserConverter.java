package com.sm.jax.utils;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.sm.jax.model.Resto;
import com.sm.jax.model.User;
 
public class UserConverter {
 
    // convert Person Object to MongoDB DBObject
    // take special note of converting id String to ObjectId
    public static Document toDocument(User p) {
 
//        BasicDBObjectBuilder builder = BasicDBObjectBuilder.start()
//                .append("name", p.getName()).append("description", p.getDescription());
//        if (p.getRestoId() != null)
//            builder = builder.append("_id", new ObjectId(p.getRestoId()));
//        return builder.get();
        Document doc = new Document();
        doc.append("name", p.getName()).append("email", p.getEmail()).append("password", p.getPassword());
        if (p.getId() != null)
            doc.append("_id", new ObjectId(p.getId()));
        return doc;
    }
 
    // convert DBObject Object to Person
    // take special note of converting ObjectId to String
    public static User toUser(Document document) {
    	User p = new User();
        p.setName((String) document.get("name"));
        p.setEmail((String) document.get("email"));
        p.setPassword((String) document.get("password"));
        ObjectId id = (ObjectId) document.get("_id");
        p.setId(id.toString());
        return p;
 
    }
     
}
