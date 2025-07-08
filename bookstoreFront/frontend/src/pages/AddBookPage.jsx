import React, { useState } from 'react';
import { Plus } from 'lucide-react';
import { addBook as addBookService } from '../services/bookService';

const initialForm = {
  isbn: '', title: '', authorName: '', publishYear: '', price: '',
  bookType: 'paper', stock: '', fileType: ''
};

const AddBookPage = ({ setBooks, setMessage }) => {
  const [form, setForm] = useState(initialForm);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const payload = {
        ...form,
        publishYear: parseInt(form.publishYear),
        price: parseFloat(form.price),
        stock: form.bookType === 'paper' ? parseInt(form.stock) : undefined,
      };
      const res = await addBookService(payload);
      
        setMessage('Book added successfully!');
        setForm(initialForm);
        const newList = await (await fetch('/api/books'));
        setBooks(newList);
     
    } catch (err) {
      setMessage('Error: ' + err.message);
    }
  };

  return (
    <div className="max-w-2xl mx-auto">
      <h2 className="text-xl font-semibold text-gray-900 mb-6">Add New Book</h2>
      <form onSubmit={handleSubmit} className="space-y-6">
          <div className="space-y-4">
            <input
              type="text"
              value={form.isbn}
              onChange={(e) => setForm({ ...form, isbn: e.target.value })}
              placeholder="ISBN"
              required
              className="w-full border rounded px-4 py-2"
            />
            <input
              type="text"
              value={form.title}
              onChange={(e) => setForm({ ...form, title: e.target.value })}
              placeholder="Title"
              required
              className="w-full border rounded px-4 py-2"
            />
            <input
              type="text"
              value={form.authorName}
              onChange={(e) => setForm({ ...form, authorName: e.target.value })}
              placeholder="Author Name"
              required
              className="w-full border rounded px-4 py-2"
            />
            <input
              type="number"
              value={form.publishYear}
              onChange={(e) => setForm({ ...form, publishYear: e.target.value })}
              placeholder="Publish Year"
              required
              className="w-full border rounded px-4 py-2"
            />
            <input
              type="number"
              value={form.price}
              onChange={(e) => setForm({ ...form, price: e.target.value })}
              placeholder="Price"
              step="0.01"
              required
              className="w-full border rounded px-4 py-2"
            />

            <select
              value={form.bookType}
              onChange={(e) => setForm({ ...form, bookType: e.target.value })}
              className="w-full border rounded px-4 py-2"
            >
              <option value="paper">Paper Book</option>
              <option value="ebook">EBook</option>
              <option value="showcase">Showcase Book</option>
            </select>

            {form.bookType === 'paper' && (
              <input
                type="number"
                value={form.stock}
                onChange={(e) => setForm({ ...form, stock: e.target.value })}
                placeholder="Stock Quantity"
                required
                className="w-full border rounded px-4 py-2"
              />
            )}

            {form.bookType === 'ebook' && (
              <input
                type="text"
                value={form.fileType}
                onChange={(e) => setForm({ ...form, fileType: e.target.value })}
                placeholder="EBook File Type (e.g. PDF, EPUB)"
                required
                className="w-full border rounded px-4 py-2"
              />
            )}
          </div>

          <button type="submit" className="w-full bg-blue-600 text-white py-3 px-6 rounded-lg font-medium flex items-center justify-center">
            <Plus className="w-5 h-5 mr-2" />
            Add Book
          </button>
        </form>
    </div>
  );
};

export default AddBookPage;
