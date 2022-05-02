import './App.scss';
import {CardInfo} from "./components/CardInfo/CardInfo";
import {toast} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
toast.configure()

function App() {

  return (
    <div className="app">
        <header className="banner"></header>
        <CardInfo/>
    </div>
  );
}

export default App;
