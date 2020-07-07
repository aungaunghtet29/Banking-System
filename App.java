package aah.dev;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class App {
	public static void main(String[] args) {
		final String url = "jdbc:mysql://localhost/onestop11";
		String user = "root";
		String pass = "aungaunghtet";
		try (Connection conn = DriverManager.getConnection(url, user, pass);
				PreparedStatement savingBalance = conn
						.prepareStatement("select * from saving_account where s_id = ?")) {
			PreparedStatement checkingBalance = conn.prepareStatement("select * from checking_account where c_id = ?");
			PreparedStatement depositeWidthDrawSaving = conn
					.prepareStatement("update saving_account set s_balance = ? where s_id=?");
			// int sbalance = widthDrawSaving(30000, 1, savingBalance,
			// depositeWidthDrawSaving);
			// System.out.println("John Balance After Saving $" + sbalance);

			int transferAmount = 1000;
			conn.setAutoCommit(false);
			widthDrawSaving(transferAmount, 1, savingBalance, depositeWidthDrawSaving);
			System.out.println(3 / 0);
			depositeSaving(transferAmount, 2, savingBalance, depositeWidthDrawSaving);
			conn.commit();
			System.out.println("Transfer Amount " + transferAmount);
			System.out.println("Transfer Finished!");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static int widthDrawSaving(int amount, int id, PreparedStatement balance, PreparedStatement widthDraw)
			throws SQLException {
		int bAmount = getBalanceSavingAccount(id, balance);
		if (amount >= 0 && (amount - bAmount) >= 0) {
			throw new IllegalArgumentException("Insuffient amount...");
		}
		widthDraw.setInt(1, (bAmount - amount));
		widthDraw.setInt(2, 1);
		widthDraw.executeUpdate();
		return getBalanceSavingAccount(id, balance);
	}

	public static int depositeSaving(int amount, int id, PreparedStatement balance, PreparedStatement deposite)
			throws SQLException {
		int bAmount = getBalanceSavingAccount(id, balance);
		deposite.setInt(1, bAmount + amount);
		deposite.setInt(2, id);
		deposite.executeUpdate();
		return getBalanceSavingAccount(id, balance);
	}

	public static int getBalanceCheckingAccount(int id, PreparedStatement pStatement) throws SQLException {
		pStatement.setInt(1, id);
		int balance = 0;
		ResultSet resultSet = pStatement.executeQuery();
		while (resultSet.next()) {
			balance = resultSet.getInt("c_balance");
		}
		return balance;
	}

	public static int getBalanceSavingAccount(int id, PreparedStatement pstate) throws SQLException {
		pstate.setInt(1, id);

		ResultSet set = pstate.executeQuery();
		int balance = 0;
		while (set.next()) {
			// System.out.println("select * from saving_account");
			balance = set.getInt("s_balance");
		}
		return balance;
	}
}
