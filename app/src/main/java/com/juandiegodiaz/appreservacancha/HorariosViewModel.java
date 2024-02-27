package com.juandiegodiaz.appreservacancha;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;

public class HorariosViewModel extends ViewModel {

    private ArrayList<String> horariosLunes = new ArrayList<>();
    private ArrayList<String> horariosMartes = new ArrayList<>();
    private ArrayList<String> horariosMiercoles = new ArrayList<>();
    private ArrayList<String> horariosJueves = new ArrayList<>();
    private ArrayList<String> horariosViernes = new ArrayList<>();
    private ArrayList<String> horariosSabado = new ArrayList<>();
    private ArrayList<String> horariosDomingo = new ArrayList<>();

    public ArrayList<String> getHorariosLunes() {
        return horariosLunes;
    }

    public void setHorariosLunes(ArrayList<String> horarios) {
        horariosLunes = horarios;
    }

    public ArrayList<String> getHorariosMartes() {
        return horariosMartes;
    }

    public void setHorariosMartes(ArrayList<String> horarios) {
        horariosMartes = horarios;
    }

    public ArrayList<String> getHorariosMiercoles() {
        return horariosMiercoles;
    }

    public void setHorariosMiercoles(ArrayList<String> horarios) {
        horariosMiercoles = horarios;
    }

    public ArrayList<String> getHorariosJueves() {
        return horariosJueves;
    }

    public void setHorariosJueves(ArrayList<String> horarios) {
        horariosJueves = horarios;
    }

    public ArrayList<String> getHorariosViernes() {
        return horariosViernes;
    }

    public void setHorariosViernes(ArrayList<String> horarios) {
        horariosViernes = horarios;
    }

    public ArrayList<String> getHorariosSabado() {
        return horariosSabado;
    }

    public void setHorariosSabado(ArrayList<String> horarios) {
        horariosSabado = horarios;
    }

    public ArrayList<String> getHorariosDomingo() {
        return horariosDomingo;
    }

    public void setHorariosDomingo(ArrayList<String> horarios) {
        horariosDomingo = horarios;
    }
}
