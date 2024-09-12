package com.incidenciasvlc.mysqlservice.service;

import com.incidenciasvlc.mysqlservice.model.Issue;
import com.incidenciasvlc.mysqlservice.model.Role;
import com.incidenciasvlc.mysqlservice.model.Status;
import com.incidenciasvlc.mysqlservice.model.User;
import com.incidenciasvlc.mysqlservice.model.Category;
import com.incidenciasvlc.mysqlservice.repository.IssueRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IssueServiceTest {

    @Mock
    private IssueRepository issueRepository;

    @InjectMocks
    private IssueService issueService;

    @Test
    void testFindAllIssues() {
        User user = new User();
        user.setId(1L);
        user.setName("Wilfredo");
        user.setEmail("wil@incidenciasvlc.es");
        user.setPassword("password123");
        user.setRole(Role.USER);

        Category category = new Category();
        category.setId(1L);
        category.setName("Vandalismo");

        Status status = new Status();
        status.setId(1L);
        status.setName("Abierta");

        Issue issue1 = new Issue();
        issue1.setId(1L);
        issue1.setTitle("Incidencia de Prueba 1");
        issue1.setDescription("Descripcion de incidencia... 1");
        issue1.setLatitude(39.4699);
        issue1.setLongitude(-0.3763);
        issue1.setUser(user);
        issue1.setCategory(category);
        issue1.setStatus(status);

        Issue issue2 = new Issue();
        issue2.setId(2L);
        issue2.setTitle("Incidencia de Prueba 2");
        issue2.setDescription("Descripcion de incidencia... 2");
        issue2.setLatitude(40.4168);
        issue2.setLongitude(-3.7038);
        issue2.setUser(user);
        issue2.setCategory(category);
        issue2.setStatus(status);

        List<Issue> mockIssues = Arrays.asList(issue1, issue2);

        when(issueRepository.findAll()).thenReturn(mockIssues);

        List<Issue> issues = issueService.findAllIssues();

        assertEquals(2, issues.size());
        verify(issueRepository, times(1)).findAll();
    }

    @Test
    void testFindIssueById_Found() {
        Long issueId = 1L;

        User user = new User();
        user.setId(1L);
        user.setName("Wilfredo");

        Category category = new Category();
        category.setId(1L);
        category.setName("Vandalismo");

        Status status = new Status();
        status.setId(1L);
        status.setName("Abierta");

        Issue mockIssue = new Issue();
        mockIssue.setId(issueId);
        mockIssue.setTitle("Incidencia de Prueba 1");
        mockIssue.setUser(user);
        mockIssue.setCategory(category);
        mockIssue.setStatus(status);

        when(issueRepository.findById(issueId)).thenReturn(Optional.of(mockIssue));

        Optional<Issue> foundIssue = issueService.findIssueById(issueId);

        assertTrue(foundIssue.isPresent());
        assertEquals(issueId, foundIssue.get().getId());
        verify(issueRepository, times(1)).findById(issueId);
    }

    @Test
    void testFindIssueById_NotFound() {
        Long issueId = 1L;

        when(issueRepository.findById(issueId)).thenReturn(Optional.empty());

        Optional<Issue> foundIssue = issueService.findIssueById(issueId);

        assertFalse(foundIssue.isPresent());
        verify(issueRepository, times(1)).findById(issueId);
    }

    @Test
    void testUpdateIssue_Success() {
        Long issueId = 1L;

        User user = new User();
        user.setId(1L);
        user.setName("Wilfredo");

        Category category = new Category();
        category.setId(1L);
        category.setName("Vandalismo");

        Status status = new Status();
        status.setId(1L);
        status.setName("Abierta");

        Issue existingIssue = new Issue();
        existingIssue.setId(issueId);
        existingIssue.setTitle("Incidencia de Prueba 1");
        existingIssue.setUser(user);
        existingIssue.setCategory(category);
        existingIssue.setStatus(status);

        Issue updateDetails = new Issue();
        updateDetails.setTitle("Incidencia Actualizada");

        when(issueRepository.findById(issueId)).thenReturn(Optional.of(existingIssue));
        when(issueRepository.save(existingIssue)).thenReturn(existingIssue);

        Issue updatedIssue = issueService.updateIssue(issueId, updateDetails);

        assertEquals("Incidencia Actualizada", updatedIssue.getTitle());
        verify(issueRepository, times(1)).findById(issueId);
        verify(issueRepository, times(1)).save(existingIssue);
    }

}
