package com.miu.flightmanagement.airlinebookingservice.service;

import com.miu.flightmanagement.airlinebookingservice.dao.BookingDao;
import com.miu.flightmanagement.airlinebookingservice.exception.RecordAlreadyPresentException;
import com.miu.flightmanagement.airlinebookingservice.exception.RecordNotFoundException;
import com.miu.flightmanagement.airlinebookingservice.model.Booking;
import com.miu.flightmanagement.airlinebookingservice.model.ScheduledFlight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

	/*
	 * Creating DAO object
	 */
	@Autowired
	BookingDao bookingDao;

	/*
	 * making new Booking
	 */
	@Override
	public ResponseEntity<Booking> createBooking(Booking newBooking) {

		Optional<Booking> findBookingById = bookingDao.findById(newBooking.getId());
		try {
			if (!findBookingById.isPresent()) {
				bookingDao.save(newBooking);
				return new ResponseEntity<Booking>(newBooking, HttpStatus.OK);
			} else
				throw new RecordAlreadyPresentException(
						"Booking with Booking Id: " + newBooking.getId() + " already exists!!");
		} catch (RecordAlreadyPresentException e) {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * update booking made
	 */
	@Override
	public Booking updateBooking(Booking changedBooking) {
		Optional<Booking> findBookingById = bookingDao.findById(changedBooking.getId());
		if (findBookingById.isPresent()) {
			bookingDao.save(changedBooking);
		} else
			throw new RecordNotFoundException(
					"Booking with Booking Id: " + changedBooking.getId() + " not exists!!");
		return changedBooking;
	}

	/*
	 * deleteing the booking
	 */

	@Override
	public String deleteBooking(Long bookingId) {

		Optional<Booking> findBookingById = bookingDao.findById(bookingId);
		if (findBookingById.isPresent()) {
			bookingDao.deleteById(bookingId);
			return "Booking Deleted!!";
		} else
			throw new RecordNotFoundException("Booking not found for the entered BookingID");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.org.service.BookingService#displayAllBooking() show all booking
	 */
	@Override
	public Iterable<Booking> displayAllBooking() {

		return bookingDao.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.org.service.BookingService#findBookingById(java.math.BigInteger)
	 * find booking by ID
	 */
	@Override
	public ResponseEntity<?> findBookingById(Long bookingId) {
		Optional<Booking> findById = bookingDao.findById(bookingId);
		try {
			if (findById.isPresent()) {
				Booking findBooking = findById.get();
				return new ResponseEntity<Booking>(findBooking, HttpStatus.OK);
			} else
				throw new RecordNotFoundException("No record found with ID " + bookingId);
		} catch (RecordNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public boolean hasBooking(Long scheduledFlightNo) {
		return bookingDao.findBookingByScheduledFlight(ScheduledFlight.builder().scheduleFlightId(scheduledFlightNo).build())
				.isPresent();
	}
}
