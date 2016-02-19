package com.sm.jax.utils;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sm.jax.model.Resto;
 
public class RestoConverter {
 
    // convert Person Object to MongoDB DBObject
    // take special note of converting id String to ObjectId
    public static Document toDocument(Resto p) {
 
//        BasicDBObjectBuilder builder = BasicDBObjectBuilder.start()
//                .append("name", p.getName()).append("description", p.getDescription());
//        if (p.getRestoId() != null)
//            builder = builder.append("_id", new ObjectId(p.getRestoId()));
//        return builder.get();
        Document doc = new Document();
        doc.append("name", p.getName()).append("description", p.getDescription());
        if (p.getRestoId() != null)
            doc.append("_id", new ObjectId(p.getRestoId()));
        return doc;
    }
 
    // convert DBObject Object to Person
    // take special note of converting ObjectId to String
    public static Resto toResto(Document document) {
    	Resto p = new Resto();
        p.setName((String) document.get("name"));
        p.setDescription((String) document.get("description"));
        ObjectId id = (ObjectId) document.get("_id");
        p.setRestoId(id.toString());
        return p;
 
    }
     
}
