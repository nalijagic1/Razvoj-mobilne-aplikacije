package ba.etf.rma21.projekat.data
/*
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Pitanje
import java.sql.Date

fun dajPitajaKviza():List<PitanjeKviz>{
    return listOf(
            //Prva godina
            //tp
            PitanjeKviz(Pitanje("Pitanje 1", "Čega je TP skraćenica?", listOf("Tehnike programiranja","To party","Tehnike prepisivanja"),0),Kviz("Kviz 1TP1","TP",Date(1616281200000),Date(1626818400000),null,5,"TP1", null),-1),
            PitanjeKviz(Pitanje("Pitanje 1", "Ko predaje TP?", listOf("Vedran Ljubović","Vensada Okanović","Željko Jurić"),2),Kviz("Kviz 3TP1","TP",Date(1616968800000),Date(1617055200000),Date(1617055200000),5,"TP1", 0.5F),2),

            PitanjeKviz(Pitanje("Pitanje 1", "Čega je TP skraćenica?", listOf("Tehnike programiranja","To party","Tehnike prepisivanja"),0),Kviz("Kviz 1TP2","TP",Date(1615503600000),Date(1615676400000),null,15,"TP2", null),2),
            PitanjeKviz(Pitanje("Pitanje 1", "Ko predaje TP?", listOf("Vedran Ljubović","Vensada Okanović","Željko Jurić"),2),Kviz("Kviz 2TP2","TP",Date(1617400800000),Date(1648936800000),null,10,"TP2", null),-1),

            //fwt
            PitanjeKviz(Pitanje("Prvo pitanje","JavaScript je:", listOf("knjiga","skriptni jezik","programerski jezik"),1),Kviz("Kviz 1FWT1","FWT",Date(1462226400000),Date(1462399200000),Date(1462312800000),15,"FWT1", 1.1F),2),
            PitanjeKviz(Pitanje("Prvo pitanje","Varijable se definišu  sa", listOf("var","rt","int"),0),Kviz("Kviz 2FWT1","FWT",Date(1462226400000),Date(1462399200000),null,15,"FWT1", null),2),
            PitanjeKviz(Pitanje("Prvo pitanje","Plavo je:", listOf("nebo","sunce","trava"),0), Kviz("Kviz 3FWT1","FWT",Date(1617487200000),Date(1622757600000),null,15,"FWT1", null),-1),

            PitanjeKviz(Pitanje("Prvo pitanje","Varijable se definišu  sa", listOf("var","rt","int"),0), Kviz("Kviz 2FWT2","FWT",Date(1462226400000),Date(1462399200000),Date(1462312800000),15,"FWT2",3F),0),
            PitanjeKviz(Pitanje("Prvo pitanje","Plavo je:", listOf("nebo","sunce","trava"),0), Kviz("Kviz 3FWT2","FWT",Date(1462226400000),Date(1462399200000),null,15,"FWT2", null),2),
            //Druga godina
            PitanjeKviz(Pitanje("Pitanje 1", "Čega je TP skraćenica?", listOf("Tehnike programiranja","To party","Tehnike prepisivanja"),0),Kviz("Kviz 1RMA1","RMA",Date(1616281200000),Date(1626818400000),null,5,"RMA1", null),-1),
            PitanjeKviz(Pitanje("Pitanje 1", "Ko predaje TP?", listOf("Vedran Ljubović","Vensada Okanović","Željko Jurić"),2),Kviz("Kviz 3RMA1","RMA",Date(1616968800000),Date(1617055200000),Date(1617055200000),5,"RMA1", 0.5F),2),

            PitanjeKviz(Pitanje("Pitanje 1", "Čega je TP skraćenica?", listOf("Tehnike programiranja","To party","Tehnike prepisivanja"),0),Kviz("Kviz 1RMA2","RMA",Date(1615503600000),Date(1615676400000),null,15,"RMA2", null),2),
            PitanjeKviz(Pitanje("Pitanje 1", "Ko predaje TP?", listOf("Vedran Ljubović","Vensada Okanović","Željko Jurić"),2),Kviz("Kviz 2RMA2","RMA",Date(1617400800000),Date(1648936800000),null,10,"RMA2", null),-1),

            PitanjeKviz(Pitanje("Prvo pitanje","JavaScript je:", listOf("knjiga","skriptni jezik","programerski jezik"),1),Kviz("Kviz 1BWT1","BWT",Date(1462226400000),Date(1462399200000),Date(1462312800000),15,"BWT1", 1.1F),2),
            PitanjeKviz(Pitanje("Prvo pitanje","Varijable se definišu  sa", listOf("var","rt","int"),0),Kviz("Kviz 2BWT1","BWT",Date(1462226400000),Date(1462399200000),null,15,"BWT1", null),2),
            PitanjeKviz(Pitanje("Prvo pitanje","Plavo je:", listOf("nebo","sunce","trava"),0), Kviz("Kviz 3BWT1","BWT",Date(1617487200000),Date(1622757600000),null,15,"BWT1", null),-1),

            PitanjeKviz(Pitanje("Prvo pitanje","Varijable se definišu  sa", listOf("var","rt","int"),0), Kviz("Kviz 2BWT2","BWT",Date(1462226400000),Date(1462399200000),Date(1462312800000),15,"BWT2",1.25F),0),
            PitanjeKviz(Pitanje("Prvo pitanje","Plavo je:", listOf("nebo","sunce","trava"),0), Kviz("Kviz 3BWT2","BWT",Date(1462226400000),Date(1462399200000),null,15,"BWT2", null),2),
            //Treca godina
            PitanjeKviz(Pitanje("Pitanje 1", "Čega je TP skraćenica?", listOf("Tehnike programiranja","To party","Tehnike prepisivanja"),0),Kviz("Kviz 1RMA1","RMA",Date(1616281200000),Date(1626818400000),null,5,"RMA1", null),-1),
            PitanjeKviz(Pitanje("Pitanje 1", "Ko predaje TP?", listOf("Vedran Ljubović","Vensada Okanović","Željko Jurić"),2),Kviz("Kviz 3RPR1","RPR",Date(1616968800000),Date(1617055200000),Date(1617055200000),5,"RPR1", 0.5F),2),

            PitanjeKviz(Pitanje("Pitanje 1", "Čega je TP skraćenica?", listOf("Tehnike programiranja","To party","Tehnike prepisivanja"),0),Kviz("Kviz 1RPR2","RPR",Date(1615503600000),Date(1615676400000),null,15,"RPR2", null),2),
            PitanjeKviz(Pitanje("Pitanje 1", "Ko predaje TP?", listOf("Vedran Ljubović","Vensada Okanović","Željko Jurić"),2),Kviz("Kviz 2RPR2","RPR",Date(1617400800000),Date(1648936800000),null,10,"RPR2", null),-1),

            PitanjeKviz(Pitanje("Prvo pitanje","JavaScript je:", listOf("knjiga","skriptni jezik","programerski jezik"),1),Kviz("Kviz 1OIS1","OIS",Date(1462226400000),Date(1462399200000),Date(1462312800000),15,"OIS1", 1.1F),2),
            PitanjeKviz(Pitanje("Prvo pitanje","Varijable se definišu  sa", listOf("var","rt","int"),0), Kviz("Kviz 2OIS1","OIS",Date(1462226400000),Date(1462399200000),null,15,"OIS1", null),2),
            PitanjeKviz(Pitanje("Prvo pitanje","Plavo je:", listOf("nebo","sunce","trava"),0), Kviz("Kviz 3OIS1","OIS",Date(1617487200000),Date(1622757600000),null,15,"OIS1", null),-1),

            PitanjeKviz(Pitanje("Prvo pitanje","Varijable se definišu  sa", listOf("var","rt","int"),0), Kviz("Kviz 2OIS2","OIS",Date(1462226400000),Date(1462399200000),Date(1462312800000),15,"OIS2",3F),0),
            PitanjeKviz(Pitanje("Prvo pitanje","Plavo je:", listOf("nebo","sunce","trava"),0),  Kviz("Kviz 3OIS2","OIS",Date(1462226400000),Date(1462399200000),null,15,"OIS2", null),2),

            //Četvrta godina
            PitanjeKviz(Pitanje("Pitanje 1", "Čega je TP skraćenica?", listOf("Tehnike programiranja","To party","Tehnike prepisivanja"),0),Kviz("Kviz 1DM1","DM",Date(1616281200000),Date(1626818400000),null,5,"DM1", null),-1),
            PitanjeKviz(Pitanje("Pitanje 1", "Ko predaje TP?", listOf("Vedran Ljubović","Vensada Okanović","Željko Jurić"),2),Kviz("Kviz 3DM1","DM",Date(1616968800000),Date(1617055200000),Date(1617055200000),5,"DM1", 0.5F),2),

            PitanjeKviz(Pitanje("Pitanje 1", "Čega je TP skraćenica?", listOf("Tehnike programiranja","To party","Tehnike prepisivanja"),0),Kviz("Kviz 1DM2","DM",Date(1615503600000),Date(1615676400000),null,15,"DM2", null),2),


            PitanjeKviz(Pitanje("Prvo pitanje","JavaScript je:", listOf("knjiga","skriptni jezik","programerski jezik"),1),Kviz("Kviz 1RP1","RP",Date(1462226400000),Date(1462399200000),Date(1462312800000),15,"RP1", 1.1F),2),
            PitanjeKviz(Pitanje("Prvo pitanje","Varijable se definišu  sa", listOf("var","rt","int"),0),Kviz("Kviz 2RP1","RP",Date(1462226400000),Date(1462399200000),null,15,"RP1", null),2),
            PitanjeKviz(Pitanje("Prvo pitanje","Plavo je:", listOf("nebo","sunce","trava"),0), Kviz("Kviz 3RP1","RP",Date(1617487200000),Date(1622757600000),null,15,"RP1", null),-1),

            PitanjeKviz(Pitanje("Prvo pitanje","Varijable se definišu  sa", listOf("var","rt","int"),0), Kviz("Kviz 2RP2","RP",Date(1462226400000),Date(1462399200000),Date(1462312800000),15,"RP2",0.3F),0),
            PitanjeKviz(Pitanje("Prvo pitanje","Plavo je:", listOf("nebo","sunce","trava"),0),   Kviz("Kviz 3RP2","RP",Date(1462226400000),Date(1462399200000),null,15,"RP2", null),2),


            PitanjeKviz(Pitanje("Pitanje1","Koji je glavni grad BiH", listOf("Srajevo","London","Austija"),0),Kviz("Kviz 2DM2","DM",Date(1617400800000),Date(1648936800000),null,10,"DM2", null),-1),
            PitanjeKviz(Pitanje("Pitanje2","Koji je glavni grad BiH", listOf("Kina","Sarajevo","Austija"),1),Kviz("Kviz 2DM2","DM",Date(1617400800000),Date(1648936800000),null,10,"DM2", null), -1),
            PitanjeKviz(Pitanje("Pitanje3","Koji je glavni grad BiH", listOf("Srajevo","Japan","Austija"),0),Kviz("Kviz 2DM2","DM",Date(1617400800000),Date(1648936800000),null,10,"DM2", null),-1),


            //Peta godina
            PitanjeKviz(Pitanje("Pitanje 1", "Čega je TP skraćenica?", listOf("Tehnike programiranja","To party","Tehnike prepisivanja"),0),Kviz("Kviz 1VIS1","VIS",Date(1616281200000),Date(1626818400000),null,5,"VIS1", null),-1),
            PitanjeKviz(Pitanje("Pitanje 1", "Ko predaje TP?", listOf("Vedran Ljubović","Vensada Okanović","Željko Jurić"),2), Kviz("Kviz 3VIS1","VIS",Date(1616968800000),Date(1617055200000),Date(1617055200000),5,"VIS1", 0.5F),2),

            PitanjeKviz(Pitanje("Pitanje 1", "Čega je TP skraćenica?", listOf("Tehnike programiranja","To party","Tehnike prepisivanja"),0),Kviz("Kviz 1VIS2","VIS",Date(1615503600000),Date(1615676400000),null,15,"VIS2", null),2),
            PitanjeKviz(Pitanje("Pitanje 1", "Ko predaje TP?", listOf("Vedran Ljubović","Vensada Okanović","Željko Jurić"),2),Kviz("Kviz 2VIS2","VIS",Date(1617400800000),Date(1648936800000),null,10,"VIS2", null),-1),


            PitanjeKviz(Pitanje("Prvo pitanje","JavaScript je:", listOf("knjiga","skriptni jezik","programerski jezik"),1),Kviz("Kviz 1ICR1","ICR",Date(1462226400000),Date(1462399200000),Date(1462312800000),15,"ICR1", 1.1F),2),
            PitanjeKviz(Pitanje("Prvo pitanje","Varijable se definišu  sa", listOf("var","rt","int"),0),Kviz("Kviz 2ICR1","ICR",Date(1462226400000),Date(1462399200000),null,15,"ICR1", null),2),
            PitanjeKviz(Pitanje("Prvo pitanje","Plavo je:", listOf("nebo","sunce","trava"),0), Kviz("Kviz 3ICR1","ICR",Date(1617487200000),Date(1622757600000),null,15,"ICR1", null),-1),

            PitanjeKviz(Pitanje("Prvo pitanje","Varijable se definišu  sa", listOf("var","rt","int"),0), Kviz("Kviz 2ICR2","ICR",Date(1462226400000),Date(1462399200000),Date(1462312800000),15,"ICR2",1.4F),0),
            PitanjeKviz(Pitanje("Prvo pitanje","Plavo je:", listOf("nebo","sunce","trava"),0),   Kviz("Kviz 3ICR2","ICR",Date(1462226400000),Date(1462399200000),null,15,"ICR2", null),2)
    )}





*/