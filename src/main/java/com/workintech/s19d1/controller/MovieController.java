package com.workintech.s19d1.controller;

import com.workintech.s19d1.dto.MovieRequest;
import com.workintech.s19d1.entity.Actor;
import com.workintech.s19d1.entity.Movie;
import com.workintech.s19d1.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/movie")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<Movie> findAll() {
        return movieService.findAll();
    }

    @GetMapping("/{id}")
    public Movie findById(@PathVariable Long id) {
        return movieService.findById(id);
    }

    @PostMapping
    public Movie save(@RequestBody MovieRequest movieRequest) {
        Movie movie = movieRequest.getMovie();
        if (movieRequest.getActors() != null) {
            for (Actor actor : movieRequest.getActors()) {
                movie.addActor(actor);
                actor.addMovie(movie);
            }
        }
        return movieService.save(movie);
    }

    @PutMapping("/{id}")
    public Movie update(@PathVariable Long id, @RequestBody Movie incomingMovie) {
        Movie movie = movieService.findById(id);
        movie.setName(incomingMovie.getName());
        movie.setDirectorName(incomingMovie.getDirectorName());
        movie.setRating(incomingMovie.getRating());
        movie.setReleaseDate(incomingMovie.getReleaseDate());
        if (incomingMovie.getActors() != null) {
            movie.setActors(incomingMovie.getActors());
        }
        return movieService.save(movie);
    }

    @DeleteMapping("/{id}")
    public Movie delete(@PathVariable Long id) {
        Movie movie = movieService.findById(id);
        movieService.delete(movie);
        return movie;
    }
}