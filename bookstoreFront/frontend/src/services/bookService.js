const BASE_URL = 'http://localhost:8080/api/books';

export const fetchBooks = async () => {
  const res = await fetch(BASE_URL);
  return await res.json();
};

export const addBook = async (book) => {
  try {
    const res = await fetch(`${BASE_URL}/${book.bookType}`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(book)
    });

    const contentType = res.headers.get("content-type");

    if (contentType && contentType.includes("application/json")) {
      const data = await res.json();

      if (!res.ok) {
        console.error("Server returned error status:", res.status, data);
        return null;
      }

      console.log("Success:", data);
      return data;
    } else {
      const text = await res.text();
      console.error("Expected JSON but got:", text);
      return null;
    }
  } catch (error) {
    console.error("Request failed:", error);
    return null;
  }
};


export const buyBook = async (purchase) => {
  const res = await fetch(`${BASE_URL}/buy`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(purchase)
  });
  return await res.json();
};

export const removeOutdatedBooks = async (years) => {
  const res = await fetch(`${BASE_URL}/outdated/${years}`, { method: 'DELETE' });
  return await res;
};
