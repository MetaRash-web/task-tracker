import 'react';
import { createRoot } from 'react-dom/client';
import Header from './containers/Header';
import Sidebar from './containers/Sidebar';
import MainContent from './containers/MainContent';

const App = () => {
    return (
        <>
            <Header />
            <div className="container">
                <Sidebar />
                <MainContent />
            </div>
        </>
    );
};

createRoot(document.getElementById('root')).render(<App />);
