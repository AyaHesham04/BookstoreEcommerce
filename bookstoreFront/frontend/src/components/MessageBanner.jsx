import React from 'react';

const MessageBanner = ({ message, onDismiss }) => {
  const isError = message.toLowerCase().includes('error') || message.toLowerCase().includes('failed');
  return (
    <div className={`mb-6 p-4 rounded-lg ${isError ? 'bg-red-100 text-red-700' : 'bg-green-100 text-green-700'}`}>
      {message}
      <button onClick={onDismiss} className="ml-4 text-sm underline hover:no-underline">
        Dismiss
      </button>
    </div>
  );
};

export default MessageBanner;
