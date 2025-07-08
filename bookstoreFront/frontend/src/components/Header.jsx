import React from 'react';
import { Book } from 'lucide-react';

const tabs = [
  { key: 'inventory', label: 'Inventory' },
  { key: 'add', label: 'Add Book' },
  { key: 'purchase', label: 'Purchase' },
  { key: 'manage', label: 'Manage' },
];


const Header = ({ activeTab, setActiveTab }) => (
  <header className="bg-white shadow-sm border-b">
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <div className="flex justify-between items-center py-6">
        <div className="flex items-center">
          <Book className="w-8 h-8 text-blue-600 mr-3" />
          <h1 className="text-2xl font-bold text-gray-900">Quantum Book Store</h1>
        </div>
        <nav className="flex space-x-4">
          {tabs.map(tab => (
            <button
              key={tab.key}
              onClick={() => setActiveTab(tab.key)}
              className={`px-4 py-2 cursor-pointer rounded-lg font-medium transition-colors ${
                activeTab === tab.key
                  ? 'bg-blue-600 text-white'
                  : 'text-gray-600 hover:text-gray-900'
              }`}
            >
              {tab.label}
            </button>
          ))}
        </nav>
      </div>
    </div>
  </header>
);

export default Header;
