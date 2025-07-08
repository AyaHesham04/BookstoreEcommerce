import React, { useState } from 'react';
import { ShoppingCart } from 'lucide-react';
import { buyBook as buyBookService } from '../services/bookService';
import { fetchBooks } from '../services/bookService';

const initialPurchase = { isbn: '', quantity: 1, email: '', address: '' };

const PurchasePage = ({ books, setBooks, setMessage }) => {
  const [form, setForm] = useState(initialPurchase);
  const [showModal, setShowModal] = useState(false);
  const [modalInfo, setModalInfo] = useState({ amount: 0, stockLeft: null });

  const selectedBook = Array.isArray(books)
    ? books.find((book) => book.isbn === form.isbn)
    : null;
  const bookType = selectedBook?.bookType;


  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const quantity = parseInt(form.quantity);
      const res = await buyBookService({
        ...form,
        quantity: parseInt(form.quantity),
      });
      if (res.success) {
        setMessage(`Purchase successful! Total amount: ${res.totalAmount} EGP`);
        setForm(initialPurchase);
        const data = await fetchBooks();
        setBooks(data);
        console.log(data);

        const updatedBook = Array.isArray(data)
          ? data.find((book) => book.isbn === form.isbn)
          : null;
        console.log(updatedBook);

        const stockLeft = updatedBook.bookType === 'Paper Book' ? updatedBook?.stock : null;
        console.log(stockLeft);
        setModalInfo({
          amount: res.totalAmount,
          stockLeft,
        });

        setShowModal(true);
        setForm(initialPurchase);
      } else {
        setMessage('Purchase failed: ' + res.message);
      }
    } catch (err) {
      setMessage('Error: ' + err.message);
    }
  };

  return (
    <div className="max-w-2xl mx-auto">
      <h2 className="text-xl font-semibold text-gray-900 mb-6">Purchase Book</h2>
      <form onSubmit={handleSubmit} className="space-y-6">
        <div className="space-y-4">
          <select
            value={form.isbn}
            onChange={(e) => setForm({ ...form, isbn: e.target.value })}
            required
            className="w-full border rounded px-4 py-2"
          >
            <option value="">Select a book (ISBN - Title)</option>
            {Array.isArray(books) && books.length > 0 ? (
              books
                .filter((book) => book.bookType !== 'Showcase Book')
                .map((book) => (
                  <option key={book.isbn} value={book.isbn}>
                    {book.isbn} - {book.title}
                  </option>
                ))
            ) : (
              <option disabled>No books available</option>
            )}

          </select>
          <input
            type="number"
            value={form.quantity}
            onChange={(e) => setForm({ ...form, quantity: e.target.value })}
            placeholder="Quantity"
            min={1}
            required
            className="w-full border rounded px-4 py-2"
          />
          {(!bookType || bookType === 'EBook') && (
            <input
              type="email"
              value={form.email}
              onChange={(e) => setForm({ ...form, email: e.target.value })}
              placeholder="Email (for EBooks)"
              required
              className="w-full border rounded px-4 py-2"
            />
          )}
          {(!bookType || bookType === 'Paper Book') && (
            <input
              type="text"
              value={form.address}
              onChange={(e) => setForm({ ...form, address: e.target.value })}
              placeholder="Shipping Address (for Paper Books)"
              required
              className="w-full border rounded px-4 py-2"
            />
          )}
        </div>

        <button type="submit" className="w-full bg-green-600 text-white py-3 px-6 rounded-lg font-medium flex items-center justify-center">
          <ShoppingCart className="w-5 h-5 mr-2" />
          Purchase Book
        </button>
      </form>
      {showModal && (
        <div className="fixed inset-0 bg-black bg-opacity-40 flex items-center justify-center z-50">
          <div className="bg-white p-6 rounded-xl shadow-lg w-80 text-center">
            <h3 className="text-lg font-semibold mb-4">Purchase Successful 🎉</h3>
            <p className="mb-2">Amount Paid: <strong>{modalInfo.amount} EGP</strong></p>
            {modalInfo.stockLeft !== null && (
              <p className="mb-4">Remaining Stock: <strong>{modalInfo.stockLeft}</strong></p>
            )}
            <button
              onClick={() => setShowModal(false)}
              className="bg-blue-600 text-white px-4 py-2 rounded"
            >
              Close
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default PurchasePage;
