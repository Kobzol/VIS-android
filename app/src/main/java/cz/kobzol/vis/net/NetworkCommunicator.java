package cz.kobzol.vis.net;


import android.content.Context;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import cz.kobzol.vis.dao.GradeDTO;
import cz.kobzol.vis.dao.PersonDTO;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Query;

interface VISService {
    @GET("IsLoginValid")
    Call<Boolean> isLoginValid(@Query("username") String username, @Query("password") String password);

    @GET("GetPerson")
    Call<PersonDTO> getPerson(@Query("id") long id);

    @GET("TestGetGrades")
    Call<List<GradeDTO>> testGetGrades();
}

class JsonDateDeserializer implements JsonDeserializer<Date> {
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String s = json.getAsJsonPrimitive().getAsString();
        long l = Long.parseLong(s.substring(6, s.length() - 7));
        return new Date(l);
    }
}

public class NetworkCommunicator
{
    private static NetworkCommunicator instance;

    public synchronized static void initInstance(Context context)
    {
        if (NetworkCommunicator.instance == null)
        {
            NetworkCommunicator.instance = new NetworkCommunicator(context.getApplicationContext());
        }
    }
    public static NetworkCommunicator getInstance()
    {
        return NetworkCommunicator.instance;
    }

    private Retrofit retrofit;
    private VISService service;

    public NetworkCommunicator(Context context)
    {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.94:5858/SchoolService.svc/rest/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().registerTypeAdapter(Date.class, new JsonDateDeserializer()).create()))
                .build();
        this.service = retrofit.create(VISService.class);
    }

    public void isLoginValid(String username, String password, final ServiceResponse<Boolean> callback)
    {
        this.service.isLoginValid(username, password).enqueue(this.getCallbackForResponse(callback));
    }

    public void getPerson(long id, final ServiceResponse<PersonDTO> callback)
    {
        this.service.getPerson(id).enqueue(this.getCallbackForResponse(callback));
    }

    public void testGetGrades(final ServiceResponse<List<GradeDTO>> callback)
    {
        this.service.testGetGrades().enqueue(this.getCallbackForResponse(callback));
    }

    private <T> Callback<T> getCallbackForResponse(final ServiceResponse<T> callback)
    {
        return new Callback<T>() {
            @Override
            public void onResponse(Response<T> response, Retrofit retrofit) {
                if (response.isSuccess())
                {
                    callback.onResult(response.body());
                }
                else
                {
                    try
                    {
                        callback.onError(new Exception(response.errorBody().string()));
                    }
                    catch (Exception e)
                    {
                        callback.onError(e);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onError(t);
            }
        };
    }
}
