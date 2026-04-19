/**
 * api.js — All API calls mapped to Spring Boot controllers
 * Base URL: http://localhost:8080
 */

const API_BASE = 'http://localhost:8080/api';

function getToken()   { return localStorage.getItem('bms_token'); }
function getUser()    { return JSON.parse(localStorage.getItem('bms_user') || 'null'); }
function isLoggedIn() { return !!getToken(); }
function isAdmin() {
  const u = getUser();
  return u && Array.isArray(u.roles) && u.roles.includes('ROLE_ADMIN');
}

function saveSession(data) {
  localStorage.setItem('bms_token', data.token);
  localStorage.setItem('bms_user', JSON.stringify({
    id: data.id ?? null,
    username: data.username,
    roles: data.roles || [],
  }));
}
function clearSession() {
  localStorage.removeItem('bms_token');
  localStorage.removeItem('bms_user');
}

async function apiFetch(path, options = {}) {
  const token = getToken();
  const headers = { 'Content-Type': 'application/json', ...(options.headers || {}) };
  if (token) headers['Authorization'] = `Bearer ${token}`;

  const res = await fetch(`${API_BASE}${path}`, { ...options, headers });

  if (res.status === 401) {
    clearSession();
    window.location.href = '/pages/login.html';
    throw new Error('Unauthorized');
  }

  if (!res.ok) {
    let msg = `HTTP ${res.status}`;
    try {
      const text = await res.text();
      if (text) {
        try {
          const b = JSON.parse(text);
          msg = b.message || b.error || text || msg;
        } catch {
          msg = text;
        }
      }
    } catch {}
    throw new Error(msg);
  }

  if (res.status === 204 || res.headers.get('content-length') === '0') return null;
  return res.json();
}

const Auth = {
  login(username, password) {
    return apiFetch('/auth/login', {
      method: 'POST',
      body: JSON.stringify({ username, password }),
    });
  },

  register(username, password, email, phone) {
    return apiFetch('/auth/user/register', {
      method: 'POST',
      body: JSON.stringify({ username, password, email, phone }),
    });
  },

  registerAdmin(username, password, email, phone) {
    return apiFetch('/auth/admin/register', {
      method: 'POST',
      body: JSON.stringify({ username, password, email, phone }),
    });
  },

  getAllUsers() { return apiFetch('/auth/admin'); },
  getUserById(id) { return apiFetch(`/auth/admin/${id}`); },
};

const Movies = {
  getAll() { return apiFetch('/movies'); },
  getById(id) { return apiFetch(`/movies/${id}`); },
  search(title) { return apiFetch(`/movies/search?title=${encodeURIComponent(title)}`); },
  byGenre(genre) { return apiFetch(`/movies/genre/${encodeURIComponent(genre)}`); },
  byLanguage(lang) { return apiFetch(`/movies/language/${encodeURIComponent(lang)}`); },
  add(movieRequest) {
    return apiFetch('/movies/admin', {
      method: 'POST',
      body: JSON.stringify(movieRequest),
    });
  },
};

const Shows = {
  getAll() { return apiFetch('/shows'); },
  getById(id) { return apiFetch(`/shows/${id}`); },
  byMovie(movieId) { return apiFetch(`/shows/movie/${movieId}`); },
  byMovieAndDate(movieId, date) {
    return apiFetch(`/shows/movie/${movieId}/date?date=${date}`);
  },
  add(showRequest) {
    return apiFetch('/shows/admin', {
      method: 'POST',
      body: JSON.stringify(showRequest),
    });
  },
};

const Theaters = {
  getAll() { return apiFetch('/theaters'); },
  getById(id) { return apiFetch(`/theaters/${id}`); },
  byCity(cityId) { return apiFetch(`/theaters/city/${cityId}`); },
  add(theaterRequest) {
    return apiFetch('/theaters/admin', {
      method: 'POST',
      body: JSON.stringify(theaterRequest),
    });
  },
};

const Screens = {
  getAll() { return apiFetch('/screens'); },
  getById(id) { return apiFetch(`/screens/screen/${id}`); },
  byTheater(theaterId) { return apiFetch(`/screens/theater/${theaterId}`); },
  add(screenRequest) {
    return apiFetch('/screens/admin', {
      method: 'POST',
      body: JSON.stringify(screenRequest),
    });
  },
};

const Seats = {
  getById(id) { return apiFetch(`/seats/${id}`); },
  byScreen(screenId) { return apiFetch(`/seats/screen/${screenId}`); },
  add(seatRequest) {
    return apiFetch('/seats/admin', {
      method: 'POST',
      body: JSON.stringify(seatRequest),
    });
  },
};

const Bookings = {
  create(showId, seatIds) {
    return apiFetch('/bookings', {
      method: 'POST',
      body: JSON.stringify({ showId, seatIds }),
    });
  },
  getById(id) { return apiFetch(`/bookings/${id}`); },
  byUser(userId) { return apiFetch(`/bookings/user/${userId}`); },
  availableSeats(showId) { return apiFetch(`/bookings/show/${showId}/available-seats`); },
  cancel(id) {
    return apiFetch(`/bookings/${id}/cancel`, { method: 'PUT' });
  },
};

const Cities = {
  getAll() { return apiFetch('/cities'); },
  getById(id) { return apiFetch(`/cities/${id}`); },
  add(name, state) {
    return apiFetch('/cities/admin', {
      method: 'POST',
      body: JSON.stringify({ name, state }),
    });
  },
};

window.API = { Auth, Movies, Shows, Theaters, Screens, Seats, Bookings, Cities };
window.Auth = { getToken, getUser, isLoggedIn, isAdmin, saveSession, clearSession };
