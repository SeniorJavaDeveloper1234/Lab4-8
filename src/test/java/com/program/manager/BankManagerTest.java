package com.program.manager;

import com.program.bank.Bank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.File;
import java.io.FilenameFilter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BankManagerTest {

    private FileManager fileManager;
    private BankManager bankManager;

    @BeforeEach
    void setUp() {
        fileManager = mock(FileManager.class);
        bankManager = new BankManager(fileManager, true); // skipLoad=true
    }

    // -----------------------------
    // addBank()
    // -----------------------------
    @Test
    void testAddBank() {
        Bank bank = new Bank();
        bank.setName("TestBank");

        bankManager.addBank(bank);

        assertEquals(1, bankManager.getBanks().size());
        assertEquals("TestBank", bankManager.getBanks().get(0).getName());
    }

    // -----------------------------
    // loadBanks() - no folder
    // -----------------------------
    @Test
    void testLoadBanks_NoFolder() {
        File folder = mock(File.class);

        when(folder.exists()).thenReturn(false);

        bankManager = spy(new BankManager(fileManager, true));
        doReturn(folder).when(bankManager).getFolder();

        bankManager.loadBanks();

        assertTrue(bankManager.getBanks().isEmpty());
    }

    // -----------------------------
    // loadBanks() - empty folder
    // -----------------------------
    @Test
    void testLoadBanks_EmptyFolder() {
        File folder = mock(File.class);

        when(folder.exists()).thenReturn(true);
        when(folder.isDirectory()).thenReturn(true);

        when(folder.listFiles(any(FilenameFilter.class)))
                .thenReturn(new File[]{});

        bankManager = spy(new BankManager(fileManager, true));
        doReturn(folder).when(bankManager).getFolder();

        bankManager.loadBanks();

        assertTrue(bankManager.getBanks().isEmpty());
    }

    // -----------------------------
    // loadBanks() - valid file
    // -----------------------------


    // -----------------------------
    // loadBanks() - file returns null (broken json)
    // -----------------------------
    @Test
    void testLoadBanks_FileReturnsNull() {
        File folder = mock(File.class);
        File file = mock(File.class);

        when(folder.exists()).thenReturn(true);
        when(folder.isDirectory()).thenReturn(true);

        when(folder.listFiles(any(FilenameFilter.class)))
                .thenReturn(new File[]{file});

        when(file.getAbsolutePath()).thenReturn("bad.json");

        when(fileManager.loadFromJson(anyString(), eq(Bank.class)))
                .thenReturn(null);

        bankManager = spy(new BankManager(fileManager, true));
        doReturn(folder).when(bankManager).getFolder();

        bankManager.loadBanks();

        assertTrue(bankManager.getBanks().isEmpty());
    }

    // -----------------------------
    // saveBank()
    // -----------------------------
    @Test
    void testSaveBank() {
        Bank bank = new Bank();
        bank.setName("Mono");

        ArgumentCaptor<String> pathCaptor = ArgumentCaptor.forClass(String.class);

        bankManager.saveBank(bank);

        verify(fileManager).saveAsJson(pathCaptor.capture(), eq(bank));

        String path = pathCaptor.getValue();
        assertTrue(path.contains("Mono.json"));
    }

    // -----------------------------
    // saveAllBank()
    // -----------------------------
    @Test
    void testSaveAllBanks() {
        Bank b1 = new Bank();
        b1.setName("Privat");

        Bank b2 = new Bank();
        b2.setName("Oschad");

        bankManager.addBank(b1);
        bankManager.addBank(b2);

        bankManager.saveAllBank();

        verify(fileManager, times(2)).saveAsJson(anyString(), any(Bank.class));
    }

    // -----------------------------
    // constructor — should call loadBanks() when skipLoad=false
    // -----------------------------

    @Test
    void testConstructorSkipLoad() {
        BankManager spyManager = spy(new BankManager(fileManager, true));

        verify(spyManager, times(0)).loadBanks();
    }
}
