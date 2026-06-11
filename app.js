// ── PRODUCTION BASE URL (Railway Backend) ──────
const BASE = "https://employee-payroll-system-production.up.railway.app/api/employees";


// ═══════════════════════════════════════════════
// 1. LOAD ALL EMPLOYEES (index.html table)
// ═══════════════════════════════════════════════
async function loadEmployees() {
  try {
    const res = await fetch(BASE);
    const employees = await res.json();

    const tbody = document.getElementById("empTable");
    if (!tbody) return;

    tbody.innerHTML = "";

    employees.forEach(e => {
      tbody.innerHTML += `
        <tr>
          <td>#${e.id}</td>
          <td><strong>${e.name}</strong></td>
          <td>${e.department}</td>
          <td>${e.designation}</td>
          <td>₹${Number(e.basicSalary).toLocaleString()}</td>
          <td>
            <button onclick="viewPayroll(${e.id})" class="btn btn-info btn-sm">💰 Payslip</button>
            <button onclick="deleteEmployee(${e.id})" class="btn btn-danger btn-sm">🗑️ Delete</button>
          </td>
        </tr>`;
    });

    loadStats();

  } catch (err) {
    console.log("Load error:", err);
  }
}


// ═══════════════════════════════════════════════
// 2. LOAD STATS
// ═══════════════════════════════════════════════
async function loadStats() {
  try {
    const res = await fetch(BASE + "/salary-bill");
    const data = await res.json();

    const row = document.getElementById("statsRow");
    if (!row) return;

    row.innerHTML = `
      <div class="col-md-4 mb-3">
        <div class="card bg-dark text-white p-3">
          <small>Total Employees</small>
          <h3>${data.totalEmployees}</h3>
        </div>
      </div>

      <div class="col-md-4 mb-3">
        <div class="card bg-warning p-3">
          <small>Total Gross Bill</small>
          <h3>₹${Number(data.totalGrossBill).toLocaleString()}</h3>
        </div>
      </div>

      <div class="col-md-4 mb-3">
        <div class="card bg-success text-white p-3">
          <small>Total Net Bill</small>
          <h3>₹${Number(data.totalNetBill).toLocaleString()}</h3>
        </div>
      </div>`;
  } catch (err) {
    console.log("Stats error:", err);
  }
}


// ═══════════════════════════════════════════════
// 3. ADD EMPLOYEE (add.html)
// ═══════════════════════════════════════════════
async function addEmployee() {

  const employee = {
    name: document.getElementById("name").value,
    email: document.getElementById("email").value,
    department: document.getElementById("department").value,
    designation: document.getElementById("designation").value,
    basicSalary: document.getElementById("basicSalary").value,
    phone: document.getElementById("phone").value,
    joinDate: document.getElementById("joinDate").value
  };

  try {
    const res = await fetch(BASE, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(employee)
    });

    if (res.ok) {
      document.getElementById("msg").innerHTML =
        '<span class="text-success">✅ Employee Added Successfully!</span>';

      setTimeout(() => {
        window.location.href = "index.html";
      }, 1200);

    } else {
      document.getElementById("msg").innerHTML =
        '<span class="text-danger">❌ Failed to add employee</span>';
    }

  } catch (err) {
    console.log(err);
    document.getElementById("msg").innerHTML =
      '<span class="text-danger">❌ Server Error</span>';
  }
}


// ═══════════════════════════════════════════════
// 4. DELETE EMPLOYEE
// ═══════════════════════════════════════════════
async function deleteEmployee(id) {
  if (!confirm("Delete this employee?")) return;

  await fetch(BASE + "/" + id, {
    method: "DELETE"
  });

  loadEmployees();
}


// ═══════════════════════════════════════════════
// 5. VIEW PAYROLL
// ═══════════════════════════════════════════════
async function viewPayroll(id) {
  const res = await fetch(BASE + "/" + id + "/payroll");
  const p = await res.json();

  alert(`
════ PAYROLL SLIP ════
Name: ${p.name}
Dept: ${p.department}
Role: ${p.designation}

Basic: ₹${p.basicSalary}
HRA: ₹${p.hra}
DA: ₹${p.da}

Gross: ₹${p.grossSalary}
PF: -₹${p.pfDeduction}

NET: ₹${p.netSalary}
══════════════════════
  `);
}


// ═══════════════════════════════════════════════
// 6. SEARCH
// ═══════════════════════════════════════════════
function searchEmployees() {
  const keyword = document.getElementById("searchBox").value.toLowerCase();
  const rows = document.querySelectorAll("#empTable tr");

  rows.forEach(row => {
    row.style.display =
      row.innerText.toLowerCase().includes(keyword) ? "" : "none";
  });
}


// ═══════════════════════════════════════════════
// 7. FILTER BY DEPARTMENT
// ═══════════════════════════════════════════════
async function filterByDept() {
  const dept = document.getElementById("deptFilter").value;

  if (!dept) {
    loadEmployees();
    return;
  }

  const res = await fetch(BASE + "/department/" + dept);
  const employees = await res.json();

  const tbody = document.getElementById("empTable");
  tbody.innerHTML = "";

  employees.forEach(e => {
    tbody.innerHTML += `
      <tr>
        <td>#${e.id}</td>
        <td>${e.name}</td>
        <td>${e.department}</td>
        <td>${e.designation}</td>
        <td>₹${Number(e.basicSalary).toLocaleString()}</td>
        <td>
          <button onclick="viewPayroll(${e.id})" class="btn btn-info btn-sm">💰 Payslip</button>
        </td>
      </tr>`;
  });
}


// ═══════════════════════════════════════════════
// AUTO LOAD
// ═══════════════════════════════════════════════
window.onload = loadEmployees;