import React, { useState } from 'react';
import { Trash2 } from 'lucide-react';
import { removeOutdatedBooks as removeService } from '../services/bookService';

const ManagePage = ({ books, setBooks, setMessage }) => {
  const [customYears, setCustomYears] = useState('');

  const handleRemove = async (years) => {
    if (!years || isNaN(years) || years < 0) {
      setMessage('Please enter a valid number of years.');
      return;
    }

    try {
      const removed = await removeService(Number(years));
      setMessage(`Removed outdated book(s)`);
      const newList = await (await fetch('/api/books'));
      setBooks(newList);
    } catch (err) {
      setMessage('Error: ' + err.message);
    }
  };

  return (
    <div className="max-w-2xl mx-auto space-y-6">
      <div className="bg-white rounded-lg shadow border p-6">
        <h3 className="text-lg font-medium mb-4">Remove Outdated Books</h3>
        <h2 className="text-sm font-medium mb-4">Enter number of years</h2>

        <div className="flex flex-col sm:flex-row gap-4 items-center">
          <input
            type="number"
            value={customYears}
            onChange={(e) => setCustomYears(e.target.value)}
            placeholder="Enter custom years"
            className="border rounded-lg px-4 py-2 flex-1"
            min="0"
          />
          <button
            onClick={() => handleRemove(customYears)}
            className="bg-blue-600 text-white py-2 px-4 rounded-lg flex items-center justify-center"
          >
            Remove Books
          </button>
        </div>
      </div>

      <div className="bg-white rounded-lg shadow border p-6">
        <h3 className="text-lg font-medium mb-4">Inventory Statistics</h3>
        <div className="grid grid-cols-2 gap-4 mb-5">
          <StatCard books={books} label="Paper Books" filterFn={b => b.bookType === 'Paper Book'} color="blue" />
          <StatCard books={books} label="EBooks" filterFn={b => b.bookType === 'EBook'} color="green" />
          <StatCard books={books} label="Showcases" filterFn={b => b.bookType === 'Showcase Book'} color="purple" />
        </div>
        <div className="space-y-4">
          <h3 className="text-lg font-medium mb-4">Inventory of Paper Books</h3>
          {books.filter((book) => book.bookType === 'Paper Book').length === 0 ? (
            <div className="text-gray-500 italic">No paper books</div>
          ) : (
            books
              .filter((book) => book.bookType === 'Paper Book')
              .map((book) => (
                <div key={book.isbn} className="border rounded-lg p-4 shadow-sm">
                  <div className="text-lg font-semibold text-gray-800">{book.title}</div>
                  <div className="text-sm text-gray-500">ISBN: {book.isbn}</div>
                  <div className="text-sm text-gray-500">Type: {book.bookType}</div>
                  <div className="text-md font-bold text-blue-600">Quantity: {book.quantity}</div>
                </div>
              ))
          )}
        </div>
      </div>
    </div>
  );
};

const StatCard = ({ books = [], label, filterFn, color }) => {
  const count = books.filter(filterFn).length;

  return (
    <div className="text-center">
      <div className={`text-2xl font-bold text-${color}-600`}>{count}</div>
      <div className="text-sm text-gray-500">{label}</div>
    </div>
  );
};


export default ManagePage;
