package com.ids.progettoids.modelsInterface;

import java.util.ArrayList;

import com.ids.progettoids.models.Content;
import com.ids.progettoids.models.Itinerario;
import com.ids.progettoids.models.POI;
import com.ids.progettoids.models.Report;

public interface CuratoreInterface {

    /**
     * Metodo per approvare un POI
     * @param poi il POI da approvare
     */
    public void ApprovaPOI(POI poi);

    /**
     * Metodo per approvare un itinerario
     * @param itinerario l'itinerario da approvare
     */
    public void ApprovaItinerari(Itinerario itinerario);

    /**
     * Metodo per approvare un content
     * @param content il content da approvare
     * @param idContent l'id del content
     */
    public void ApprovaContent(Content content, int idContent);

    
    /**
     * Metodo per aggiornare l'id di un POI
     * @param _idNuovo del poi nuovo
     * @param _idVecchio del poi vecchio
     */
    public void AggiornaId(int _idNuovo, int _idVecchio);


    /**
     * Metodo per editare un POI
     * @param base POI vecchio
     * @param editato POI nuovo
     */
    public void EditaPOI(POI base, POI editato);


    /**
     * Metodo per editare un Content
     * @param base Content vecchio
     * @param editato Content nuovo
     */
    public void EditaContent(Content base, Content editato);


    /**
     * Metodo per editare un Itinerario
     * @param base Itinerario vecchio
     * @param editato Itinerario nuovo
     */
    public void EditaItinerario(Itinerario base, Itinerario editato);


    /**
     * Metodo per leggere i Report e salvarli in liste
     */
    public void VisualizzaReport();


    /**
     * Metodo per leggere il Report con una chiave specifica
     * @param chiave del Report
     * @return Report
     */
    public Report getReportFromChiave(String chiave);


    /**
     * Metodo per eliminare un Report
     * @param r Report da eliminare
     */
    public void EliminaReport(Report r);


    /**
     * Metodo per eliminare una segnalazione
     * @param chiave del Report
     */
    public void EliminaSegnalazione(String chiave);


    /**
     * Metodo per leggere tutti i Report
     * @return tutti i Report
     */
    public ArrayList<Report> GetReports();
}
