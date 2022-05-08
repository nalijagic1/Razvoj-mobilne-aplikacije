/*package ba.etf.rma21.projekat.data

import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Kviz
import java.sql.Date

fun sviKvizovi(): List<Kviz> {
    return listOf(
            //aktivni,budući,urađeni,propušteni
            //Prva godina
            Kviz("Kviz 1TP1","TP",Date(1616281200000),Date(1626818400000),null,5,"TP1", null),
            Kviz("Kviz 2TP1","TP",Date(1626818400000),Date(1629496800000),null,5,"TP1", null),
            Kviz("Kviz 3TP1","TP",Date(1616968800000),Date(1617055200000),Date(1617055200000),5,"TP1", 0.5F),

            Kviz("Kviz 1TP2","TP",Date(1615503600000),Date(1615676400000),null,15,"TP2", null),
            Kviz("Kviz 2TP2","TP",Date(1617400800000),Date(1648936800000),null,10,"TP2", null),
            Kviz("Kviz 3TP2","TP",Date(1648936800000),Date(1651528800000),null,20,"TP2", null),

            Kviz("Kviz 1FWT1","FWT",Date(1462226400000),Date(1462399200000),Date(1462312800000),15,"FWT1", 1.1F),
            Kviz("Kviz 2FWT1","FWT",Date(1462226400000),Date(1462399200000),null,15,"FWT1", null),
            Kviz("Kviz 3FWT1","FWT",Date(1617487200000),Date(1622757600000),null,15,"FWT1", null),

            Kviz("Kviz 1FWT2","FWT",Date(1622757600000),Date(1625349600000),null,15,"FWT2", null),
            Kviz("Kviz 2FWT2","FWT",Date(1462226400000),Date(1462399200000),Date(1462312800000),15,"FWT2",3F),
            Kviz("Kviz 3FWT2","FWT",Date(1462226400000),Date(1462399200000),null,15,"FWT2", null),

            //Druga godina

            Kviz("Kviz 1RMA1","RMA",Date(1616281200000),Date(1626818400000),null,5,"RMA1", null),
            Kviz("Kviz 2RMA1","RMA",Date(1626818400000),Date(1629496800000),null,5,"RMA1", null),
            Kviz("Kviz 3RMA1","RMA",Date(1616968800000),Date(1617055200000),Date(1617055200000),5,"RMA1", 0.5F),

            Kviz("Kviz 1RMA2","RMA",Date(1615503600000),Date(1615676400000),null,15,"RMA2", null),
            Kviz("Kviz 2RMA2","RMA",Date(1617400800000),Date(1648936800000),null,10,"RMA2", null),
            Kviz("Kviz 3RMA2","RMA",Date(1648936800000),Date(1651528800000),null,20,"RMA2", null),

            Kviz("Kviz 1BWT1","BWT",Date(1462226400000),Date(1462399200000),Date(1462312800000),15,"BWT1", 1.1F),
            Kviz("Kviz 2BWT1","BWT",Date(1462226400000),Date(1462399200000),null,15,"BWT1", null),
            Kviz("Kviz 3BWT1","BWT",Date(1617487200000),Date(1622757600000),null,15,"BWT1", null),

            Kviz("Kviz 1BWT2","BWT",Date(1622757600000),Date(1625349600000),null,15,"BWT2", null),
            Kviz("Kviz 2BWT2","BWT",Date(1462226400000),Date(1462399200000),Date(1462312800000),15,"BWT2",1.25F),
            Kviz("Kviz 3BWT2","BWT",Date(1462226400000),Date(1462399200000),null,15,"BWT2", null),

            //Treća godina
            Kviz("Kviz 1RPR1","RPR",Date(1616281200000),Date(1626818400000),null,5,"RPR1", null),
            Kviz("Kviz 2RPR1","RPR",Date(1626818400000),Date(1629496800000),null,5,"RPR1", null),
            Kviz("Kviz 3RPR1","RPR",Date(1616968800000),Date(1617055200000),Date(1617055200000),5,"RPR1", 0.5F),

            Kviz("Kviz 1RPR2","RPR",Date(1615503600000),Date(1615676400000),null,15,"RPR2", null),
            Kviz("Kviz 2RPR2","RPR",Date(1617400800000),Date(1648936800000),null,10,"RPR2", null),
            Kviz("Kviz 3RPR2","RPR",Date(1648936800000),Date(1651528800000),null,20,"RPR2", null),

            Kviz("Kviz 3OIS1","OIS",Date(1617487200000),Date(1622757600000),null,15,"OIS1", null),
            Kviz("Kviz 1OIS1","OIS",Date(1462226400000),Date(1462399200000),Date(1462312800000),15,"OIS1", 1.1F),
            Kviz("Kviz 2OIS1","OIS",Date(1462226400000),Date(1462399200000),null,15,"OIS1", null),


            Kviz("Kviz 1OIS2","OIS",Date(1622757600000),Date(1625349600000),null,15,"OIS2", null),
            Kviz("Kviz 2OIS2","OIS",Date(1462226400000),Date(1462399200000),Date(1462312800000),15,"OIS2",3F),
            Kviz("Kviz 3OIS2","OIS",Date(1462226400000),Date(1462399200000),null,15,"OIS2", null),

            //Četvrta godina
            Kviz("Kviz 1DM1","DM",Date(1616281200000),Date(1626818400000),null,5,"DM1", null),
            Kviz("Kviz 2DM1","DM",Date(1626818400000),Date(1629496800000),null,5,"DM1", null),
            Kviz("Kviz 3DM1","DM",Date(1616968800000),Date(1617055200000),Date(1617055200000),5,"DM1", 0.5F),

            Kviz("Kviz 1DM2","DM",Date(1615503600000),Date(1615676400000),null,15,"DM2", null),
            Kviz("Kviz 2DM2","DM",Date(1617400800000),Date(1648936800000),null,10,"DM2", null),
            Kviz("Kviz 3DM2","DM",Date(1648936800000),Date(1651528800000),null,20,"DM2", null),

            Kviz("Kviz 1RP1","RP",Date(1462226400000),Date(1462399200000),Date(1462312800000),15,"RP1", 1.1F),
            Kviz("Kviz 2RP1","RP",Date(1462226400000),Date(1462399200000),null,15,"RP1", null),
            Kviz("Kviz 3RP1","RP",Date(1617487200000),Date(1622757600000),null,15,"RP1", null),

            Kviz("Kviz 1RP2","RP",Date(1622757600000),Date(1625349600000),null,15,"RP2", null),
            Kviz("Kviz 2RP2","RP",Date(1462226400000),Date(1462399200000),Date(1462312800000),15,"RP2",0.3F),
            Kviz("Kviz 3RP2","RP",Date(1462226400000),Date(1462399200000),null,15,"RP2", null),

            //Peta godina
            Kviz("Kviz 1VIS1","VIS",Date(1616281200000),Date(1626818400000),null,5,"VIS1", null),
            Kviz("Kviz 2VIS1","VIS",Date(1626818400000),Date(1629496800000),null,5,"VIS1", null),
            Kviz("Kviz 3VIS1","VIS",Date(1616968800000),Date(1617055200000),Date(1617055200000),5,"VIS1", 0.5F),

            Kviz("Kviz 1VIS2","VIS",Date(1615503600000),Date(1615676400000),null,15,"VIS2", null),
            Kviz("Kviz 2VIS2","VIS",Date(1617400800000),Date(1648936800000),null,10,"VIS2", null),
            Kviz("Kviz 3VIS2","VIS",Date(1648936800000),Date(1651528800000),null,20,"VIS2", null),

            Kviz("Kviz 1ICR1","ICR",Date(1462226400000),Date(1462399200000),Date(1462312800000),15,"ICR1", 1.1F),
            Kviz("Kviz 2ICR1","ICR",Date(1462226400000),Date(1462399200000),null,15,"ICR1", null),
            Kviz("Kviz 3ICR1","ICR",Date(1617487200000),Date(1622757600000),null,15,"ICR1", null),

            Kviz("Kviz 1ICR2","ICR",Date(1622757600000),Date(1625349600000),null,15,"ICR2", null),
            Kviz("Kviz 2ICR2","ICR",Date(1462226400000),Date(1462399200000),Date(1462312800000),15,"ICR2",1.4F),
            Kviz("Kviz 3ICR2","ICR",Date(1462226400000),Date(1462399200000),null,15,"ICR2", null)

        )
}

*/