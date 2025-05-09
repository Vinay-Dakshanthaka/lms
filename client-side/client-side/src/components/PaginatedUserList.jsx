import React, { useEffect, useState } from "react";
import axios from "axios";
import "bootstrap/dist/css/bootstrap.min.css";

const PaginatedUserList = () => {
  const [users, setUsers] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [loading, setLoading] = useState(true);

  const fetchUsers = async (pageNum) => {
    setLoading(true);
    try {
      const response = await axios.get(`http://localhost:8080/api/user/getPaginatedUsers?page=${pageNum}&size=10`);
      const { content, totalPages } = response.data.data;
      console.log(response)
      setUsers(content);
      setTotalPages(totalPages);
      setPage(pageNum);
    } catch (error) {
      console.error("Error fetching users:", error);
    }
    setLoading(false);
  };

  useEffect(() => {
    fetchUsers(0);
  }, []);

  const handlePageChange = (pageNum) => {
    if (pageNum >= 0 && pageNum < totalPages) {
      fetchUsers(pageNum);
    }
  };

  return (
    <div className="container mt-4">
      <h3 className="mb-4">Paginated User List</h3>

      {loading ? (
        <div className="text-center">Loading...</div>
      ) : (
        <>
          <table className="table table-bordered table-hover">
            <thead className="table-dark">
              <tr>
                <th>ID</th>
                <th>Picture</th>
                <th>Name</th>
                <th>Email</th>
                <th>Role</th>
                <th>Given Name</th>
                <th>Family Name</th>
              </tr>
            </thead>
            <tbody>
              {users.map((user) => (
                <tr key={user.id}>
                  <td>{user.id}</td>
                  <td>
                    <img
                      src={user.pictureUrl}
                      referrerPolicy="no-referrer"
                      alt="Profile"
                      style={{ width: "40px", height: "40px", borderRadius: "50%" }}
                    />
                  </td>
                  <td>{user.name}</td>
                  <td>{user.email}</td>
                  <td>{user.role}</td>
                  <td>{user.givenName}</td>
                  <td>{user.familyName}</td>
                </tr>
              ))}
            </tbody>
          </table>

          {/* Pagination controls */}
          <nav>
            <ul className="pagination justify-content-center">
              <li className={`page-item ${page === 0 ? "disabled" : ""}`}>
                <button className="page-link" onClick={() => handlePageChange(page - 1)}>Previous</button>
              </li>
              {[...Array(totalPages).keys()].map((p) => (
                <li key={p} className={`page-item ${p === page ? "active" : ""}`}>
                  <button className="page-link" onClick={() => handlePageChange(p)}>
                    {p + 1}
                  </button>
                </li>
              ))}
              <li className={`page-item ${page === totalPages - 1 ? "disabled" : ""}`}>
                <button className="page-link" onClick={() => handlePageChange(page + 1)}>Next</button>
              </li>
            </ul>
          </nav>
        </>
      )}
    </div>
  );
};

export default PaginatedUserList;
