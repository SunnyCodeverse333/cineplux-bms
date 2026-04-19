CinePlex frontend (patched)

What was fixed
- Registration now sends username, password, email, phone.
- Session storage now keeps user id when backend sends it.
- Admin payloads now match your DTOs:
  - MovieRequest: title, genre, language, durationMinutes, rating, releaseDate
  - TheaterRequest: name, address, cityId
  - ScreenRequest: name, totalSeats, theaterId
  - SeatRequest: seatNumber, row, col, seatType, screenId
  - ShowRequest: movieId, screenId, showDate, startTime, endTime, ticketPrice
  - City entity/controller: name, state
- Bookings page and movie detail page now use row/seatNumber instead of rowLabel.

One backend change is still required
- Your /api/auth/login response must include the logged-in user's id.
  The frontend now stores data.id, but bookings for normal users still need the backend to send it.
