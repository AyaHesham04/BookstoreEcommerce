import React, { useEffect, useState } from 'react';
import Header from './components/Header';
import InventoryPage from './pages/InventoryPage';
import AddBookPage from './pages/AddBookPage';
import PurchasePage from './pages/PurchasePage';
import ManagePage from './pages/ManagePage';
import { fetchBooks } from './services/bookService';
import MessageBanner from './components/MessageBanner';

function App() {
  const [books, setBooks] = useState([]);
  const [activeTab, setActiveTab] = useState('inventory');
  const [searchTerm, setSearchTerm] = useState('');
  const [message, setMessage] = useState('');
  const [purchaseResult, setPurchaseResult] = useState(null);

  useEffect(() => {
    (async () => {
      try {
        const data = await fetchBooks();
        setBooks(data);
      } catch (err) {
        setMessage('Error fetching books: ' + err.message);
      }
    })();
  }, []);

  return (
    <div className="min-h-screen bg-gray-50">
      <Header activeTab={activeTab} setActiveTab={setActiveTab} />
      <main className="max-w-7xl mx-auto p-6">
        {message && <MessageBanner message={message} onDismiss={() => setMessage('Error fetching books: ' + err.message)} />}
        {activeTab === 'inventory' && <InventoryPage books={books} searchTerm={searchTerm} setSearchTerm={setSearchTerm} />}
        {activeTab === 'add' && <AddBookPage setBooks={setBooks} setMessage={setMessage} />}
        {activeTab === 'purchase' && <PurchasePage books={books} setBooks={setBooks} setMessage={setMessage} />}
        {activeTab === 'manage' && <ManagePage books={books} setBooks={setBooks} setMessage={setMessage} />}
      </main>
    </div>
  );
}

export default App;
