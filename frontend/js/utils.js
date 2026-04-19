/**
 * utils.js — Shared UI helpers
 */

// ════════════════════════════════════════════════════════════════════
// TOAST NOTIFICATIONS
// ════════════════════════════════════════════════════════════════════
(function initToasts() {
  const container = document.createElement('div');
  container.className = 'toast-container';
  document.body.appendChild(container);
  window._toastContainer = container;
})();

function showToast(message, type = 'info', duration = 3500) {
  const icons = { success: '✓', error: '✕', info: '◆' };
  const toast = document.createElement('div');
  toast.className = `toast toast--${type}`;
  toast.innerHTML = `
    <span class="toast__icon">${icons[type] || icons.info}</span>
    <span class="toast__msg">${message}</span>
  `;
  window._toastContainer.appendChild(toast);
  setTimeout(() => {
    toast.classList.add('removing');
    toast.addEventListener('animationend', () => toast.remove());
  }, duration);
}

// ════════════════════════════════════════════════════════════════════
// NAVBAR  (inject into any page)
// ════════════════════════════════════════════════════════════════════
function initNavbar(activePage) {
  const nav = document.getElementById('main-nav');
  if (!nav) return;

  const user = window.Auth.getUser();
  const admin = window.Auth.isAdmin();
  const loggedIn = window.Auth.isLoggedIn();

  // Resolve relative paths based on location
  const isRoot = !window.location.pathname.includes('/pages/');
  const root   = isRoot ? '' : '../';

  const navLinks = [
    { href: `${root}index.html`,          label: 'Home',     key: 'home' },
    { href: `${root}pages/movies.html`,   label: 'Movies',   key: 'movies' },
    { href: `${root}pages/theaters.html`, label: 'Theaters', key: 'theaters' },
    ...(loggedIn ? [{ href: `${root}pages/bookings.html`, label: 'My Bookings', key: 'bookings' }] : []),
    ...(admin    ? [{ href: `${root}pages/admin.html`,    label: 'Admin',       key: 'admin' }]    : []),
  ];

  nav.innerHTML = `
    <a class="navbar__logo" href="${root}index.html">CINE<span>PLEX</span></a>
    <nav>
      <ul class="navbar__nav" id="nav-links">
        ${navLinks.map(l => `
          <li><a href="${l.href}" class="${l.key === activePage ? 'active' : ''}">${l.label}</a></li>
        `).join('')}
      </ul>
    </nav>
    <div class="navbar__spacer"></div>
    <div class="navbar__actions" id="nav-user-info">
      ${loggedIn ? `
        <span class="nav-username">Hi, <strong>${user.username}</strong></span>
        <button class="btn btn-ghost btn-sm" onclick="logoutUser()">Logout</button>
      ` : `
        <a href="${root}pages/login.html"    class="btn btn-ghost btn-sm">Login</a>
        <a href="${root}pages/register.html" class="btn btn-primary btn-sm">Register</a>
      `}
    </div>
    <button class="navbar__hamburger" id="hamburger" aria-label="Menu">
      <span></span><span></span><span></span>
    </button>
  `;

  // hamburger toggle
  const hamburger = document.getElementById('hamburger');
  const navLinks2 = document.getElementById('nav-links');
  hamburger?.addEventListener('click', () => {
    navLinks2.classList.toggle('open');
  });
}

function logoutUser() {
  window.Auth.clearSession();
  const isRoot = !window.location.pathname.includes('/pages/');
  window.location.href = isRoot ? 'index.html' : '../index.html';
}
window.logoutUser = logoutUser;

// ════════════════════════════════════════════════════════════════════
// MOVIE CARD  builder
// ════════════════════════════════════════════════════════════════════
function buildMovieCard(movie, onClick) {
  const isRoot = !window.location.pathname.includes('/pages/');
  const detailUrl = `${isRoot ? 'pages/' : ''}movie-detail.html?id=${movie.id}`;

  const card = document.createElement('div');
  card.className = 'card movie-card';
  card.innerHTML = `
    <div class="movie-card__poster">
      ${movie.posterUrl
        ? `<img class="movie-card__poster-img" src="${movie.posterUrl}" alt="${movie.title}" loading="lazy">`
        : `<div class="movie-card__poster-fallback">🎬<small>${movie.language || ''}</small></div>`
      }
      <div class="movie-card__overlay">
        <button class="btn btn-primary btn-sm">Book Now</button>
      </div>
      ${movie.rating ? `<div class="movie-card__badge">★ ${movie.rating}</div>` : ''}
    </div>
    <div class="movie-card__info">
      <div class="movie-card__title" title="${movie.title}">${movie.title}</div>
      <div class="movie-card__meta">
        ${movie.genre ? `<span class="movie-card__genre">${movie.genre}</span>` : ''}
        ${movie.durationMinutes ? `<span>${movie.durationMinutes}m</span>` : ''}
      </div>
    </div>
  `;
  card.addEventListener('click', () => {
    if (onClick) onClick(movie);
    else window.location.href = detailUrl;
  });
  return card;
}

// ════════════════════════════════════════════════════════════════════
// SKELETON LOADERS
// ════════════════════════════════════════════════════════════════════
function renderMovieSkeletons(container, count = 8) {
  container.innerHTML = '';
  for (let i = 0; i < count; i++) {
    container.innerHTML += `
      <div class="card" style="overflow:hidden">
        <div class="skeleton" style="aspect-ratio:2/3;width:100%"></div>
        <div style="padding:.9rem;display:flex;flex-direction:column;gap:.5rem">
          <div class="skeleton" style="height:14px;border-radius:4px;width:80%"></div>
          <div class="skeleton" style="height:12px;border-radius:4px;width:50%"></div>
        </div>
      </div>`;
  }
}

// ════════════════════════════════════════════════════════════════════
// DATE HELPERS
// ════════════════════════════════════════════════════════════════════
function formatDate(dateStr) {
  if (!dateStr) return '—';
  const d = new Date(dateStr);
  return d.toLocaleDateString('en-IN', { day: 'numeric', month: 'short', year: 'numeric' });
}
function formatTime(timeStr) {
  if (!timeStr) return '—';
  const [h, m] = timeStr.split(':');
  const hr = parseInt(h);
  const ampm = hr >= 12 ? 'PM' : 'AM';
  const h12 = hr % 12 || 12;
  return `${h12}:${m} ${ampm}`;
}
function formatDateTime(dt) {
  if (!dt) return '—';
  const d = new Date(dt);
  return d.toLocaleString('en-IN', { day: 'numeric', month: 'short', year: 'numeric', hour: '2-digit', minute: '2-digit' });
}
function getNextDays(n = 7) {
  const days = [];
  const dayNames = ['Sun','Mon','Tue','Wed','Thu','Fri','Sat'];
  for (let i = 0; i < n; i++) {
    const d = new Date();
    d.setDate(d.getDate() + i);
    days.push({
      label: i === 0 ? 'Today' : i === 1 ? 'Tmrw' : dayNames[d.getDay()],
      date: d.toISOString().split('T')[0],
      d,
    });
  }
  return days;
}
function todayStr() { return new Date().toISOString().split('T')[0]; }

// ════════════════════════════════════════════════════════════════════
// MODAL HELPERS
// ════════════════════════════════════════════════════════════════════
function openModal(id) {
  const el = document.getElementById(id);
  if (el) { el.classList.add('open'); document.body.style.overflow = 'hidden'; }
}
function closeModal(id) {
  const el = document.getElementById(id);
  if (el) { el.classList.remove('open'); document.body.style.overflow = ''; }
}
// Close on backdrop click
document.addEventListener('click', e => {
  if (e.target.classList.contains('modal-backdrop')) {
    e.target.classList.remove('open');
    document.body.style.overflow = '';
  }
});

// ════════════════════════════════════════════════════════════════════
// BADGE HELPER
// ════════════════════════════════════════════════════════════════════
function statusBadge(status) {
  const map = {
    CONFIRMED: ['badge-confirmed', '✓ Confirmed'],
    CANCELLED: ['badge-cancelled', '✕ Cancelled'],
    PENDING:   ['badge-pending',   '⏳ Pending'],
  };
  const [cls, label] = map[status] || ['badge-pending', status];
  return `<span class="badge ${cls}">${label}</span>`;
}

// ════════════════════════════════════════════════════════════════════
// GUARD: require login
// ════════════════════════════════════════════════════════════════════
function requireLogin() {
  if (!window.Auth.isLoggedIn()) {
    const isRoot = !window.location.pathname.includes('/pages/');
    window.location.href = `${isRoot ? 'pages/' : ''}login.html?redirect=${encodeURIComponent(window.location.href)}`;
    return false;
  }
  return true;
}
function requireAdmin() {
  if (!requireLogin()) return false;
  if (!window.Auth.isAdmin()) {
    showToast('Admin access required', 'error');
    const isRoot = !window.location.pathname.includes('/pages/');
    window.location.href = isRoot ? 'index.html' : '../index.html';
    return false;
  }
  return true;
}

// ════════════════════════════════════════════════════════════════════
// RENDER MOVIES LIST (reusable)
// ════════════════════════════════════════════════════════════════════
function renderMovies(movies, container) {
  container.innerHTML = '';
  if (!movies.length) {
    container.innerHTML = `
      <div class="empty-state" style="grid-column:1/-1">
        <div class="empty-state__icon">🎬</div>
        <div class="empty-state__title">No movies found</div>
        <div class="empty-state__sub">Try a different search or filter</div>
      </div>`;
    return;
  }
  movies.forEach(m => container.appendChild(buildMovieCard(m)));
}

// ════════════════════════════════════════════════════════════════════
// QUERY PARAMS
// ════════════════════════════════════════════════════════════════════
function getParam(name) {
  return new URLSearchParams(window.location.search).get(name);
}

// Expose globally
window.Utils = {
  showToast, initNavbar, buildMovieCard, renderMovieSkeletons,
  formatDate, formatTime, formatDateTime, getNextDays, todayStr,
  openModal, closeModal, statusBadge, requireLogin, requireAdmin,
  renderMovies, getParam,
};
// Shorthand
window.showToast = showToast;
window.openModal = openModal;
window.closeModal = closeModal;
