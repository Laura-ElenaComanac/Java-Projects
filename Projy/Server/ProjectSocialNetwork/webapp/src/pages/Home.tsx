import {IonButton, IonContent, IonHeader, IonItem, IonLabel, IonPage, IonTitle, IonToolbar} from '@ionic/react';
import ExploreContainer from '../components/ExploreContainer';
import './Home.css';

const Home: React.FC = () => {
  return (
    <IonPage>
      <IonHeader>
        <IonToolbar>
          <IonTitle>Projy</IonTitle>
        </IonToolbar>
      </IonHeader>
      <IonContent fullscreen>
        <IonHeader collapse="condense">
          <IonToolbar>
            <IonTitle size="large">Blank</IonTitle>
          </IonToolbar>
        </IonHeader>
          <IonItem routerLink="/login">
              <IonButton>Login</IonButton>
          </IonItem>
        <ExploreContainer />
      </IonContent>
    </IonPage>
  );
};

export default Home;
