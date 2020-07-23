package me.SuperRonanCraft.BetterEconomy;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class EconomyImplementer implements Economy {

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double v) {
        return null;
    }

    @Override
    public String currencyNamePlural() {
        return null;
    }

    @Override
    public String currencyNameSingular() {
        return null;
    }

    @Override
    public boolean hasAccount(String s) {
        return false;
    }

    @Override
    public boolean hasAccount(OfflinePlayer player) {
        return false;
    }

    @Override
    public boolean hasAccount(String s, String s1) {
        return false;
    }

    @Override
    public boolean hasAccount(OfflinePlayer player, String s) {
        return false;
    }

    @Override
    public double getBalance(String s) {
        Player player = Bukkit.getPlayer(s);
        UUID id = player.getUniqueId();
        return getPl().playerBank.getOrDefault(id, 0.0);
    }

    @Override
    public double getBalance(OfflinePlayer player) {
        UUID id = player.getUniqueId();
        return getPl().playerBank.getOrDefault(id, 0.0);
    }

    @Override
    public double getBalance(String s, String s1) {
        Player player = Bukkit.getPlayer(s);
        UUID id = player.getUniqueId();
        return getPl().playerBank.getOrDefault(id, 0.0);
    }

    @Override
    public double getBalance(OfflinePlayer player, String s) {
        UUID id = player.getUniqueId();
        return getPl().playerBank.getOrDefault(id, 0.0);
    }

    @Override
    public boolean has(String s, double v) {
        return false;
    }

    @Override
    public boolean has(OfflinePlayer player, double v) {
        return false;
    }

    @Override
    public boolean has(String s, String s1, double v) {
        return false;
    }

    @Override
    public boolean has(OfflinePlayer player, String s, double v) {
        return false;
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, double v) {
        return null;
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, String s1, double v) {
        return null;
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(String s, double amt) {
        Player player = Bukkit.getPlayer(s);
        UUID id = player.getUniqueId();
        getPl().playerBank.put(id, getPl().playerBank.get(id) + amt);
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, double amt) {
        UUID id = player.getUniqueId();
        getPl().playerBank.put(id, getPl().playerBank.get(id) + amt);
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(String s, String s1, double amt) {
        Player player = Bukkit.getPlayer(s);
        UUID id = player.getUniqueId();
        getPl().playerBank.put(id, getPl().playerBank.get(id) + amt);
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, String s, double amt) {
        UUID id = player.getUniqueId();
        getPl().playerBank.put(id, getPl().playerBank.get(id) + amt);
        return null;
    }

    @Override
    public EconomyResponse createBank(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse createBank(String s, OfflinePlayer player) {
        return null;
    }

    @Override
    public EconomyResponse deleteBank(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankBalance(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankHas(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String s, OfflinePlayer player) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String s, OfflinePlayer player) {
        return null;
    }

    @Override
    public List<String> getBanks() {
        return null;
    }

    @Override
    public boolean createPlayerAccount(String s) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(String s, String s1) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player, String s) {
        return false;
    }
    
    private BetterEconomy getPl() {
        return BetterEconomy.getInstance;
    }
}
