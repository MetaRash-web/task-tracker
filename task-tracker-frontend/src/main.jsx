import 'react';
import { createRoot } from 'react-dom/client';
import Sidebar from './containers/sidebar/Sidebar.jsx';
import MainContent from './containers/MainContent';
import './style.css';

const App = () => {
    return (
        <>
            <div className="container">
                <Sidebar />
                <MainContent />
            </div>
        </>
    );
};

createRoot(document.getElementById('root')).render(<App />);
