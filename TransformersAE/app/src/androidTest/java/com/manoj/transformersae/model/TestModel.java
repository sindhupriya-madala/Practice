package com.manoj.transformersae.model;

import android.content.Context;

import com.manoj.transformersae.TestUtill.Utill;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;

/**
 * Created by Manoj Vemuru on 2018-09-22.
 */
public class TestModel extends Model {

    private String mResponseDirectory = "responses";
    private static TestModel TESTMODEL;

    private TestModel(Context context) {
        super(context, true);
    }

    public static TestModel getInstance(Context context) {
        if(TESTMODEL == null) {
            TESTMODEL = new TestModel(context);
        }
        return TESTMODEL;
    }

    public void loadUsers() {
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream(mResponseDirectory + "/transformers.json" );
            if(is != null) {
                String jsonString = IOUtils.toString(is, "UTF-8");
                List<BotModel> list = Utill.Companion.parseResponseToList(jsonString);
                insertTransformersDB(list);
                getMLiveData().postValue(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAllTransformers() {
        DB.botDao().deleteAll();
    }

}
