using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Services;
using TouristAttractions.model;
using TouristAttractionsCS.utils;

namespace TouristAttractionsCS.controller
{
    public partial class TripsForm : Form, IObserver
    {
        public ClientController clientController;
        public LoginForm loginForm;
        public AgencyUser AgencyUser;

        public TripsForm(ClientController clientController)
        {
            InitializeComponent();
            this.clientController = clientController;
        }

        internal void SetLogin(LoginForm loginForm)
        {
            this.loginForm = loginForm;
        }

        internal void SetAgencyUser(AgencyUser AgencyUser)
        {
            this.AgencyUser = AgencyUser;
            userFillLabel.Text = AgencyUser.UserName;
        }

        public void updateRows()
        {
            foreach (DataGridViewRow row in searchedTripsDataGridView.Rows)
                if (Convert.ToInt32(row.Cells["NrSeats"].Value) == 0)
                {
                    row.DefaultCellStyle.BackColor = Color.Red;
                }

            foreach (DataGridViewRow row in tripsDataGridView.Rows)
                if (Convert.ToInt32(row.Cells["NrSeats"].Value) == 0)
                {
                    row.DefaultCellStyle.BackColor = Color.Red;

                }

        }

        public void update()
        {
            try
            {
                tripsDataGridView.DataSource = clientController.FindAllTrips();
                if(touristAttracionTextBox.Text != "" && hour1TextBox.Text != "" && hour2TextBox.Text!="")
                    searchedTripsDataGridView.DataSource = clientController.SearchTripByTouristAttractionAndLeavingHour(touristAttracionTextBox.Text, TimeSpan.Parse(hour1TextBox.Text), TimeSpan.Parse(hour2TextBox.Text));
            }
            catch(Exception e)
            {
                MessageBox.Show(e.Message, "Error");
            }
            updateRows();
        }

        private void searchTripsButton_Click(object sender, EventArgs e)
        {
            try
            {
                searchedTripsDataGridView.DataSource = clientController.SearchTripByTouristAttractionAndLeavingHour(touristAttracionTextBox.Text, TimeSpan.Parse(hour1TextBox.Text), TimeSpan.Parse(hour2TextBox.Text));
            }
            catch(Exception exc)
            {
                MessageBox.Show(exc.Message, "Error");
            }
        }

        private void bookButton_Click(object sender, EventArgs e)
        {
            try
            {
                String client = clientNameTextBox.Text;
                String telephone = telephoneTextBox.Text;
                int nrTickets = int.Parse(nrTicketsTextBox.Text);

                String TouristAttraction = searchedTripsDataGridView.SelectedRows[0].Cells["TouristAttraction"].Value.ToString();
                String TransportCompany = searchedTripsDataGridView.SelectedRows[0].Cells["TransportCompany"].Value.ToString();
                TimeSpan LeavingHour = TimeSpan.Parse(searchedTripsDataGridView.SelectedRows[0].Cells["LeavingHour"].Value.ToString());
                double Price = double.Parse(searchedTripsDataGridView.SelectedRows[0].Cells["Price"].Value.ToString());
                int NrSeats = int.Parse(searchedTripsDataGridView.SelectedRows[0].Cells["NrSeats"].Value.ToString());
                int Id = int.Parse(searchedTripsDataGridView.SelectedRows[0].Cells["Id"].Value.ToString());


                Trip trip = new Trip(Id, TouristAttraction, TransportCompany, LeavingHour, Price, NrSeats);

                if (trip.NrSeats - nrTickets < 0)
                    MessageBox.Show("There are no available seats!", "Error");
                else
                {
                    trip.NrSeats = trip.NrSeats - nrTickets;
                    clientController.UpdateTrip(trip);

                    Reservation reservation = new Reservation(clientController.GetReservationsSize() + 1, nrTickets, client, telephone, AgencyUser.Id, trip.Id);
                    clientController.AddReservation(reservation);

                    clientController.NotifyServer();
                }
            }
            catch (Exception exc)
            {
                MessageBox.Show(exc.Message + '\n' + exc.StackTrace, "Error");
            }
        }

        private void logoutButton_Click(object sender, EventArgs e)
        {
            this.Hide();
            loginForm.Show();
        }

        delegate void SetDataCallback(DataGridView tripsDgv, IEnumerable<Trip> trips);

        public void BookedTrip(IEnumerable<Trip> trips)
        {
            Console.WriteLine("bookedTrip1");
            tripsDataGridView.BeginInvoke(new SetDataCallback(this.SetSourcesTrips), new object[] { tripsDataGridView, trips });
            Console.WriteLine("bookedTrip2");
            searchedTripsDataGridView.BeginInvoke(new SetDataCallback(this.SetSourcesSearchedTrips), new object[] { searchedTripsDataGridView, trips });
            Console.WriteLine("bookedTrip3");
        }

        public void SetSourcesTrips(DataGridView tripsDgv, IEnumerable<Trip> trips)
        {
            tripsDgv.DataSource = null;
            tripsDgv.DataSource = trips;
            Console.WriteLine("SetSourcesTrips");
            updateRows();
        }

        public void SetSourcesSearchedTrips(DataGridView searchedTripsDgv, IEnumerable<Trip> trips)
        {
            List<Trip> searchedTrips = (from trip in trips
                                        where trip.TouristAttraction.Equals(touristAttracionTextBox.Text) &&
                                                trip.LeavingHour.CompareTo(TimeSpan.Parse(hour1TextBox.Text)) > 0 &&
                                                trip.LeavingHour.CompareTo(TimeSpan.Parse(hour2TextBox.Text)) < 0
                                        select trip).ToList();

            searchedTripsDgv.DataSource = null;
            searchedTripsDgv.DataSource = searchedTrips;
            updateRows();
            Console.WriteLine("SetSourcesSearchedTrips");
        }
    }
}
