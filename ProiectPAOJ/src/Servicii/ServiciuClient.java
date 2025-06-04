package Servicii;

import Entitati.Gara;
import Entitati.Ruta;
import Entitati.Tren;
import Repozitorii.RuteRepo;
import Utile.NivelExperienta;
import Utile.ScrieAudit;
import Utile.Stare;

import java.time.LocalDateTime;

public class ServiciuClient {
    public static int calculeazaBilet(Ruta r){
        int cost = 40;
        cost += (r.getDestinatii().size() - 2) * 10;
        Stare stareLocomotiva = r.getTren().getLocomotiva().getStare();
        switch(stareLocomotiva){
            case NOUA:
                cost += 20;
            case UTILIZATA:
                cost += 10;
            case AVARIATA:
                cost -= 10;
            default:
                break;
        }
        NivelExperienta nivelMecanic = r.getTren().getMecanic().getNivel();
        switch(nivelMecanic){
            case VETERAN:
                cost += 20;
            case EXPERIMENTAT:
                cost += 10;
            default:
                break;
        }
        cost -= (200 - r.getTren().getLocomotiva().getVitezaMedie()) / 4;
        ScrieAudit.scrieFisier("calculat pret bilet", LocalDateTime.now());
        return cost;
    }
    public static Ruta gasesteRuta(String garaPlecare, String garaSosire){
        ScrieAudit.scrieFisier("ruta cautata in baza de date", LocalDateTime.now());
        for(Ruta r : RuteRepo.getRute()){
            boolean gasitPlecare = false;
            for(Gara g : r.getDestinatii()){
                if (g.getNume().equals(garaPlecare))
                    gasitPlecare = true;
                if(gasitPlecare && g.getNume().equals(garaSosire)){
                    if(r.getTren() != null) {
                        System.out.println("S a gasit ruta:\n" + r.toString() + "\noperator: " + r.getTren().getOperator().getNume() + "\ncost bilet: " + ServiciuClient.calculeazaBilet(r) + " lei");
                        return r;
                    }
                    else{
                        System.out.println("S a gasit o ruta insa aceasta nu are asignat nici-un tren");
                    }
                }
            }
        }
        System.out.println("Ruta specificata nu a fost gasita");
        return null;
    }
    public static Ruta gasesteRutaFiltru(String garaPlecare, String garaSosire, NivelExperienta nivelMecanic, Stare stareLocomotiva){
        ScrieAudit.scrieFisier("ruta cautata in baza de date cu filtru", LocalDateTime.now());
        for(Ruta r : RuteRepo.getRute()){
            boolean gasitPlecare = false;
            Tren tren = r.getTren();
            if(tren.getMecanic().getNivel().equals(nivelMecanic) && tren.getLocomotiva().getStare().equals(stareLocomotiva)) {
                for (Gara g : r.getDestinatii()) {
                    if (g.getNume().equals(garaPlecare))
                        gasitPlecare = true;
                    if (gasitPlecare && g.getNume().equals(garaSosire)) {
                        System.out.println("S a gasit ruta:\n" + r.toString() + "\noperator: " + r.getTren().getOperator().getNume() + "\ncost bilet: " + ServiciuClient.calculeazaBilet(r) + " lei");
                        return r;
                    }
                }
            }
        }
        System.out.println("Ruta specificata nu a fost gasita");
        return null;
    }
}
