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
    private LiveData<List<Guidance>> allGuidanceCO2;
    private LiveData<List<Guidance>> allGuidanceHumidity;
    private LiveData<List<Guidance>> allGuidanceTemperature;

    private GuidanceRepository(Application application){
        GuidanceDatabase database = GuidanceDatabase.getInstance(application);
        guidanceDao = database.noteDao();
        allGuidanceCO2 = guidanceDao.getAllCO2Guidance();
        allGuidanceHumidity = guidanceDao.getAllHumidityGuidance();
        allGuidanceTemperature=guidanceDao.getAllTemperatureGuidance();

    }

    public static synchronized GuidanceRepository getInstance(Application application){
        if(instance == null)
            instance = new GuidanceRepository(application);

        return instance;
    }

    public LiveData<List<Guidance>> getAllGuidanceCO2(){
        return allGuidanceCO2;
    }

    public LiveData<List<Guidance>> getAllGuidanceHumidity(){
        return allGuidanceHumidity;
    }

    public LiveData<List<Guidance>> getAllGuidanceTemperature(){
        return allGuidanceTemperature;
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

    public void delete(Guidance guidance)
    {
        new DeleteGuidanceAsyncTask(guidanceDao).execute(guidance);
    }

    private static class DeleteGuidanceAsyncTask extends AsyncTask<Guidance, Void, Void>
    {
        private GuidanceDao guidanceDao;

        private DeleteGuidanceAsyncTask(GuidanceDao guidanceDao)
        {
            this.guidanceDao = guidanceDao;
        }

        @Override
        protected Void doInBackground(Guidance... guidances)
        {
            guidanceDao.delete(guidances[0]);
            return null;
        }
    }

    public void update(Guidance guidance) {
        new UpdateGuidanceAsyncTask(guidanceDao).execute(guidance);
    }

    private static class UpdateGuidanceAsyncTask extends AsyncTask<Guidance, Void, Void> {
        private GuidanceDao guidanceDAO;

        private UpdateGuidanceAsyncTask(GuidanceDao guidanceDAO) {
            this.guidanceDAO = guidanceDAO;
        }

        @Override
        protected Void doInBackground(Guidance... guidances) {
            guidanceDAO.update(guidances[0]);
            return null;
        }
    }
}
