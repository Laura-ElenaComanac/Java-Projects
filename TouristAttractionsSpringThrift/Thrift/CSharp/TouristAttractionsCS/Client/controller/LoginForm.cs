using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using TouristAttractions.model;
using TouristAttractionsCS.controller;
using Services;
using TouristAttractionsCS.utils;
using transformer;

namespace TouristAttractionsCS
{
    public partial class LoginForm : Form
    {
        public ClientController clientController;
        public TripsForm tripsForm;
        public int port;

        public LoginForm(ClientController clientController)
        {
            InitializeComponent();
            this.clientController = clientController;
        }

        public void setTripsForm(TripsForm tripsForm, int port)
        {
            this.tripsForm = tripsForm;
            this.port = port;
        }

        public void bookedTrip(IEnumerable<TouristAttractions.model.Trip> trips)
        {
            throw new NotImplementedException();
        }

        private void loginButton_Click(object sender, EventArgs e)
        {
            TouristAttractions.model.AgencyUser agencyUser = new TouristAttractions.model.AgencyUser(-1, usernameTextBox.Text, passwordTextBox.Text);

            clientController.Login(agencyUser);

            TouristAttractions.model.AgencyUser searchedAgencyUser = clientController.SearchAgencyUserByUserNameAndPassword(usernameTextBox.Text, passwordTextBox.Text);

            if (searchedAgencyUser != null)
            {
                clientController.server.addObserver(port);
                showTripsDialog(searchedAgencyUser);
            }
            else
                MessageBox.Show("This Agency User doesn't exist!", "Error");
        }

        private void showTripsDialog(TouristAttractions.model.AgencyUser agencyUser)
        {
            try
            {
                tripsForm.SetAgencyUser(agencyUser);
                tripsForm.Show();
                tripsForm.updateRows();
                tripsForm.update();
            }
            catch (Exception e)
            {
                MessageBox.Show(e.Message, "Error");
            }
        }
    }
}
