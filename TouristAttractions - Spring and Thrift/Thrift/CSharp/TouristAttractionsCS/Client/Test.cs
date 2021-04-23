using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TouristAttractionsCS
{
    class Test
    {/*
        XmlConfigurator.Configure(new System.IO.FileInfo("C:\\visual projects\\TouristAttractionsCSharp\\TouristAttractionsCSharp\\log4net.config"));
            //Console.Write("Hello");

        AgencyUserDbRepository agencyUserDbRepository = new AgencyUserDbRepository();

        Console.WriteLine("Found:");
        Console.WriteLine(agencyUserDbRepository.FindOne(1));

        AgencyUser agencyUser = new AgencyUser(4, "AddedAgencyUser", "AddedPass");
        agencyUserDbRepository.Save(agencyUser);

        Console.WriteLine("Added:");
        foreach (AgencyUser agencyUser2 in agencyUserDbRepository.FindAll())
        {
            Console.WriteLine(agencyUser2);
        }

        AgencyUser agencyUser3 = new AgencyUser(4, "UpdatedAgencyUser", "UpdatedPass");
        agencyUserDbRepository.Update(agencyUser3);

        Console.WriteLine("Updated:");
        foreach (AgencyUser agencyUser4 in agencyUserDbRepository.FindAll())
        {
            Console.WriteLine(agencyUser4);
        }

        agencyUserDbRepository.Delete(4);

        Console.WriteLine("Deleted:");
        foreach (AgencyUser agencyUser5 in agencyUserDbRepository.FindAll())
        {
            Console.WriteLine(agencyUser5);
        }

        Console.WriteLine("Found2:");
        Console.WriteLine(agencyUserDbRepository.SearchAgencyByNameAndPassword("elo", "12345"));*/

        /*TripDbRepository tripDbRepository = new TripDbRepository();

        Console.WriteLine("Found:");
        Console.WriteLine(tripDbRepository.FindOne(2));

        Trip newTrip = new Trip(10, "NewAttraction4", "NewTransport4", TimeSpan.Parse("10:00"), 0, 100);
        tripDbRepository.Save(newTrip);

        Console.WriteLine("Added:");
        foreach (Trip trip in tripDbRepository.FindAll())
        {
            Console.WriteLine(trip);
        }

        Trip updatedTrip = new Trip(10, "UpdatedAttraction4", "UpdatedTransport4", TimeSpan.Parse("11:00"), 10, 200);
        tripDbRepository.Update(updatedTrip);

        Console.WriteLine("Updated:");
        foreach (Trip trip in tripDbRepository.FindAll())
        {
            Console.WriteLine(trip);
        }

        tripDbRepository.Delete(4);

        Console.WriteLine("Deleted:");
        foreach (Trip trip in tripDbRepository.FindAll())
        {
            Console.WriteLine(trip);
        }

        Console.WriteLine("Found2:");
        List<Trip> trips2 = tripDbRepository.SearchTripByTouristAttractionAndLeavingHour("Acropolis", TimeSpan.Parse("08:00"), TimeSpan.Parse("10:00"));
        foreach (Trip trip in trips2)
        {
            Console.WriteLine(trip);
        }*/

        /*ReservationDbRepository reservationDbRepository = new ReservationDbRepository();

        Console.WriteLine("Found:");
        Console.WriteLine(reservationDbRepository.FindOne(2));

        Reservation newReservation = new Reservation(10, 100, "NewClient10", "NewTelephone10", 1, 1);
        reservationDbRepository.Save(newReservation);

        Console.WriteLine("Added:");
        foreach (Reservation reservation in reservationDbRepository.FindAll())
        {
            Console.WriteLine(reservation);
        }


        Reservation updatedReservation = new Reservation(10, 100, "UpdatedClient", "UpdatedTelephone", 1, 1);
        reservationDbRepository.Update(updatedReservation);

        Console.WriteLine("Updated:");
        foreach (Reservation reservation in reservationDbRepository.FindAll())
        {
            Console.WriteLine(reservation);
        }

        reservationDbRepository.Delete(10);

        Console.WriteLine("Deleted:");
        foreach (Reservation reservation1 in reservationDbRepository.FindAll())
        {
            Console.WriteLine(reservation1);
        }

        Console.WriteLine("Found2:");
        List<Reservation> reservations2 = reservationDbRepository.SearchReservationByClientNameAndTelephone("Laura", "1234567890");
        foreach (Reservation reservation1 in reservations2)
        {
            Console.WriteLine(reservation1);

        }
        */
    }
}
