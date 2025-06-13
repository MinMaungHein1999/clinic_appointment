package com.clinic.appointment.test;


import com.clinic.appointment.dto.DoctorDto;
import com.clinic.appointment.model.Doctor;
import com.clinic.appointment.repository.DoctorRepository;
import com.clinic.appointment.service.DoctorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorService doctorService;

    @Test
    void testFindById(){
       Doctor doctor = new Doctor();
       doctor.setId(1L);
       doctor.setDob(LocalDate.of(1999, 7, 1));
       doctor.setName("Min Min");
       doctor.setPhone("094545544");
       doctor.setAddress("Yangon");

       when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));

       DoctorDto doctorDtoResult = doctorService.findById(1L);

       assertEquals("Min Min", doctorDtoResult.getName());
       assertEquals("094545544", doctorDtoResult.getPhone());
       assertEquals("Yangon", doctorDtoResult.getAddress());
       assertEquals(25, doctorDtoResult.getAge());

       System.out.println("Passed Doctor Create test.....");
    }

}
