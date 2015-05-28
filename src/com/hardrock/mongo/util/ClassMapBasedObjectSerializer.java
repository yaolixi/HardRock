package com.hardrock.mongo.util;


import java.util.List;

import org.bson.util.ClassMap;

import com.mongodb.Bytes;
import com.mongodb.util.ObjectSerializer;

/**
 * Objects of type ClassMapBasedObjectSerializer are constructed to perform
 * instance specific object to JSON serialization schemes.
 * <p>
 * This class is not thread safe
 *
 * @author breinero
 */
class ClassMapBasedObjectSerializer extends AbstractObjectSerializer {

    /**
     * Assign a ObjectSerializer to perform a type specific serialization scheme
     * @param c this object's type serves as a key in the serialization map.
     * ClassMapBasedObjectSerializer uses org.bson.util.ClassMap and not only checks if 'c' is a key in the Map,
     * but also walks the up superclass and interface graph of 'c' to find matches.
     * This means that it is only necessary assign ObjectSerializers to base classes. @see org.bson.util.ClassMap
     * @param serializer performs the serialization mapping specific to the @param key type
     */
    void addObjectSerializer(Class c, ObjectSerializer serializer) {
        _serializers.put(c , serializer);
    }
    
    /**
     * 
     * @param obj the object to be serialized
     * @param buf StringBuilder containing the JSON representation of the object
     */
    @Override
    public void serialize(Object obj, StringBuilder buf){
            
        obj = Bytes.applyEncodingHooks( obj );
        
        if(obj == null) {
            buf.append(" null ");
            return;
        }
        
        ObjectSerializer serializer = null;
        
        List<Class<?>> ancestors;
        ancestors = ClassMap.getAncestry(obj.getClass());

        for (final Class<?> ancestor : ancestors) {
            serializer = _serializers.get(ancestor);
            if (serializer != null)
                break;
        }
        
        if (serializer == null && obj.getClass().isArray())
            serializer = _serializers.get(Object[].class);
        
        if (serializer == null)
            throw new RuntimeException( "json can't serialize type : " + obj.getClass() );
        
        serializer.serialize(obj, buf);
    }
    
    private ClassMap<ObjectSerializer> _serializers = new ClassMap<ObjectSerializer>();
}
