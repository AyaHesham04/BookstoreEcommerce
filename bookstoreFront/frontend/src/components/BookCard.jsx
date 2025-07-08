import React from 'react';
import { Book, Package, FileText, Eye } from 'lucide-react';

const BookCard = ({ book }) => {
  const getIcon = () => {
    switch (book.bookType) {
      case 'Paper Book':
        return <Package className="w-5 h-5 text-blue-500" />;
      case 'EBook':
        return <FileText className="w-5 h-5 text-green-500" />;
      case 'Showcase Book':
        return <Eye className="w-5 h-5 text-purple-500" />;
      default:
        return <Book className="w-5 h-5 text-gray-500" />;
    }
  };

  const getAvailabilityBadge = () => {
    if (book.bookType === 'Showcase Book') {
      return <span className="badge purple">Not for Sale</span>;
    }
    if (book.bookType === 'Paper Book') {
      return book.stock > 0 
        ? <span className="badge green">Stock: {book.stock}</span>
        : <span className="badge red">Out of Stock</span>;
    }
    return <span className="badge green">Available</span>;
  };

  return (
    <div className="bg-white rounded-lg shadow-sm border p-6">
      <div className="flex justify-between mb-4">
        <div className="flex items-center space-x-2">
          {getIcon()}
          <span className="text-sm font-medium text-gray-600">{book.bookType}</span>
        </div>
        {getAvailabilityBadge()}
      </div>
      <h3 className="text-lg font-semibold text-gray-900 mb-2">{book.title}</h3>
      <p className="text-gray-600 mb-1">by {book.authorName}</p>
      <p className="text-sm text-gray-500 mb-1">ISBN: {book.isbn}</p>
      <p className="text-sm text-gray-500 mb-3">Published: {book.publishYear}</p>
      <div className="flex justify-between">
        <span className="text-lg font-bold text-blue-600">{book.price} EGP</span>
        {book.fileType && <span className="text-sm text-gray-500">Format: {book.fileType}</span>}
      </div>
    </div>
  );
};

export default BookCard;
