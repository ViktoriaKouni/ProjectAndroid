package com.example.sep4android.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.sep4android.Database.GuidanceDao;
import com.example.sep4android.Database.GuidanceDatabase;
import com.example.sep4android.Models.Guidance;

import java.util.List;


public class GuidanceRepository {
    

    private GuidanceDao guidanceDao;
    private static GuidanceRepository instance;
    private LiveData<List<Guidance>> allGuidance;

    private GuidanceRepository(Application application){
        GuidanceDatabase database = GuidanceDatabase.getInstance(application);
        guidanceDao = database.noteDao();
        allGuidance = guidanceDao.getAllCO2Guidance();
    }

    public static synchronized GuidanceRepository getInstance(Application application){
        if(instance == null)
            instance = new GuidanceRepository(application);

        return instance;
    }

    public LiveData<List<Guidance>> getAllGuidanceCO2(){
        return allGuidance;
    }

    public void insert(Guidance guidance) {
        new InsertNoteAsync(guidanceDao).execute(guidance);
    }


    private static class InsertNoteAsync extends AsyncTask<Guidance,Void,Void> {
        private GuidanceDao guidanceDao;

        private InsertNoteAsync(GuidanceDao guidanceDao) {
            this.guidanceDao = guidanceDao;
        }

        @Override
        protected Void doInBackground(Guidance...guidances) {
            guidanceDao.insert(guidances[0]);
            return null;
        }
    }



}
