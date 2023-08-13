package repository;

import exception.NotFoundException;
import model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepository {

    private final ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();

    private final AtomicLong idPost = new AtomicLong();

    public List<Post> all() {
        return new ArrayList<>(posts.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(posts.get(id));
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            long id = idPost.incrementAndGet();
            post.setId(id);
            posts.put(id, post);
        } else if (post.getId() != 0) {
            Long currentId = post.getId();
            posts.put(currentId, post);
        }
        return post;
    }

    public void removeById(long id) {
        if (posts.containsKey(id)) {
            posts.remove(id);
        } else {
            throw new NotFoundException("Не найден элемент с id - " + id);
        }
    }
}