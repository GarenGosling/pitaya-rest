package org.garen.pitaya.serializable;

import org.garen.pitaya.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

@Service
public class SerializedObjHandler<T extends SerializedObj>{

    @Autowired
    SerializableHandler javaSerializable;

    public void store(T t) throws Exception{
        String botPath = ResourceUtils.toURI(ClassLoader.getSystemResource("data/BOT.X")).getSchemeSpecificPart();
        String realPath = botPath.replace("BOT.X", t.getName()+".obj");
        File objFile = new File(realPath);
        if(!objFile.exists()){
            objFile.createNewFile();
        }
        SerializableHandler serializableHandler = new SerializableHandler();
        serializableHandler.store(t, new FileOutputStream(objFile));
    }

    public T load(String name) throws IOException, ClassNotFoundException, URISyntaxException {
        String botPath = ResourceUtils.toURI(ClassLoader.getSystemResource("data/BOT.X")).getSchemeSpecificPart();
        String realPath = botPath.replace("BOT.X", name+".obj");
        File objFile = new File(realPath);
        if(!objFile.exists()){
            return null;
        }
        SerializableHandler serializableHandler = new SerializableHandler();
        return (T) serializableHandler.load(new FileInputStream(objFile));
    }

    public void destroy(String name) throws URISyntaxException {
        String botPath = ResourceUtils.toURI(ClassLoader.getSystemResource("data/BOT.X")).getSchemeSpecificPart();
        String realPath = botPath.replace("BOT.X", name+".obj");
        File objFile = new File(realPath);
        if(!objFile.exists()){
            throw new BusinessException("名称有误");
        }
        objFile.delete();
    }

}
